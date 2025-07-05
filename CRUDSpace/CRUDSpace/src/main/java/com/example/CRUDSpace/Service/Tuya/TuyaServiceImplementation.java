package com.example.CRUDSpace.Service.Tuya;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.StateHistoryMapper;
import com.example.CRUDSpace.Model.DTO.Tuya.DeviceProperties;
import com.example.CRUDSpace.Model.DTO.Tuya.TuyaSpaceDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;
import com.example.CRUDSpace.Model.Entity.EquipmentState;
import com.example.CRUDSpace.Model.Entity.StateHistory;
import com.example.CRUDSpace.Model.Entity.Value;
import com.example.CRUDSpace.Repository.EquipmentRepository;
import com.example.CRUDSpace.Repository.EquipmentStateRepository;
import com.example.CRUDSpace.Repository.StateHistoryRepository;
import com.example.CRUDSpace.Repository.ValueRepository;
import com.example.CRUDSpace.utils.TuyaUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TuyaServiceImplementation implements TuyaServiceInterface {

        EquipmentRepository equipmentRepository;
        ValueRepository valueRepository;
        EquipmentStateRepository equipmentStateRepository;
        StateHistoryRepository stateHistoryRepository;
        StateHistoryMapper stateHistoryMapper;

        TuyaUtils tuyaUtils;

        public TuyaSpaceDTO getSpaceById(String spaceId) throws Exception {
                String url = "space/" + spaceId;

                String response = tuyaUtils.callTuyaAPIs(url, "GET");

                TuyaSpaceDTO result = tuyaUtils.parseTuyaResult(response, TuyaSpaceDTO.class);

                return result;
        }

        public List<DeviceProperties> getDevicePropertiesById(String deviceId)
                        throws Exception {

                String url = "thing/" + deviceId
                                + "/shadow/properties?codes=water_use_data,month_water_data,daily_water_data,switch_cold,flow_rate_instan,water_temp";

                String response = tuyaUtils.callTuyaAPIs(url, "GET");

                String[] fields = { "properties" };

                List<DeviceProperties> result = tuyaUtils.parseTuyaResultAsList(response, fields,
                                DeviceProperties.class);

                saveEquipmentState(deviceId, result);

                return result;
        }

        private void saveEquipmentState(String deviceId, List<DeviceProperties> properties) {
                Equipment equipment = equipmentRepository.findByDeviceId(deviceId)
                                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

                Set<String> codes = properties.stream().map(DeviceProperties::getCode).collect(Collectors.toSet());

                List<Value> values = valueRepository.findAllByValueNameIn(codes);

                Map<String, Value> valueMap = values.stream()
                                .collect(Collectors.toMap(Value::getValueName, Function.identity()));

                List<EquipmentState> equipmentStates = equipmentStateRepository.findAllByEquipmentIdAndValueIdIn(
                                deviceId,
                                values.stream().map(Value::getValueId).collect(Collectors.toSet()));

                List<StateHistory> stateHistories = equipmentStates.stream()
                                .map(each -> stateHistoryMapper.toStateHistory(each)).toList();

                stateHistoryRepository.saveAll(stateHistories);

                Map<String, EquipmentState> equipmenetStateMap = equipmentStates.stream()
                                .collect(Collectors.toMap(each -> each.getValue().getValueName(), Function.identity()));

                List<EquipmentState> statesToSave = new ArrayList<>();

                for (DeviceProperties dp : properties) {
                        Value value = valueMap.get(dp.getCode());

                        if (value == null) {
                                value = Value.builder()
                                                .valueName(dp.getCode())
                                                .build();
                                valueRepository.save(value);
                        }

                        EquipmentState existing = equipmenetStateMap.get(value.getValueName());

                        if (existing != null) {
                                existing.setTimeStamp(dp.getTime());
                                existing.setValueResponse(dp.getValue());
                                statesToSave.add(existing);
                        } else {
                                statesToSave.add(EquipmentState.builder()
                                                .timeStamp(dp.getTime())
                                                .valueResponse(dp.getValue())
                                                .equipment(equipment)
                                                .deviceId(deviceId)
                                                .value(value)
                                                .build());
                        }
                }

                equipmentStateRepository.saveAll(statesToSave);
        }
}