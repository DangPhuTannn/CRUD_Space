package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;
import com.example.CRUDSpace.Model.Entity.EquipmentState;
import com.example.CRUDSpace.Model.Entity.Value;

public interface EquipmentStateRepository extends JpaRepository<EquipmentState, UUID> {

    @Query("""
            Select new com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO(
                es.equipmentStateId,
                new com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO(
                    e.equipmentId,e.equipmentName
                ),
                new com.example.CRUDSpace.Model.DTO.Value.ValueDTO(
                    v.valueId,v.valueName
                ),
                es.timeStamp,es.valueResponse
            )
            From EquipmentState es
            Join es.equipment e
            Join es.value v
            """)
    List<EquipmentStateDTO> getAllEquipmentState();

    @Query("""
            Select new com.example.CRUDSpace.Model.DTO.EquipmentState.EquipmentStateDTO(
                es.equipmentStateId,
                new com.example.CRUDSpace.Model.DTO.Equipment.EquipmentDTO(
                    e.equipmentId,e.equipmentName
                ),
                new com.example.CRUDSpace.Model.DTO.Value.ValueDTO(
                    v.valueId,v.valueName
                ),
                es.timeStamp,es.valueResponse
            )
            From EquipmentState es
            Join es.equipment e
            Join es.value v
            Where es.equipment.equipmentId = :equipmentId
            """)
    List<EquipmentStateDTO> getAllEquipmentStateByEquipmentId(@Param("equipmentId") UUID equipmentId);

    @Query(value = """
            SELECT * FROM equipment_state
            WHERE device_id = :deviceId
              AND value_id IN (:valueIds)
            """, nativeQuery = true)
    List<EquipmentState> findAllByEquipmentIdAndValueIdIn(
            @Param("deviceId") String deviceId,
            @Param("valueIds") Set<UUID> valueIds);

    boolean existsByEquipmentAndValue(Equipment equipment, Value value);
}
