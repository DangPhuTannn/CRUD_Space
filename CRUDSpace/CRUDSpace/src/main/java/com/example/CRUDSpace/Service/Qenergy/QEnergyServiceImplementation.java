package com.example.CRUDSpace.Service.Qenergy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.CRUDSpace.Exception.AppException;
import com.example.CRUDSpace.Exception.ErrorCode;
import com.example.CRUDSpace.Mapper.QEnergyMapper;
import com.example.CRUDSpace.Model.DTO.QEnergy.QEnergyDTO;
import com.example.CRUDSpace.Model.DTO.QEnergy.QEneryTotalConsumptionDateRangeDTO;
import com.example.CRUDSpace.Model.Entity.QEnergy;
import com.example.CRUDSpace.Model.Entity.Space;
import com.example.CRUDSpace.Repository.QEnergyRepository;
import com.example.CRUDSpace.Repository.SpaceRepository;
import com.example.CRUDSpace.utils.QEnergyUtils;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QEnergyServiceImplementation {

    final QEnergyUtils qenergyUtils;
    final QEnergyRepository qEnergyRepository;
    final SpaceRepository spaceRepository;
    final QEnergyMapper qEnergyMapper;
    final BigDecimal ZERO = BigDecimal.ZERO;
    final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(2405, 1, 1, 0, 0, 0);
    final BigDecimal ONE_MINUTE_IN_HOUR = BigDecimal.valueOf(0.016666666666666666);
    
    // dùng để lấy siteId cho QEnergy
    private volatile Map<UUID, Space> cachedSpacesWithBlockType = new ConcurrentHashMap<>();

    public QEnergyDTO getEnergyConsumptionBySpaceId(UUID spaceId) {

        checkSpaceHavingSiteId(spaceId);

        QEnergy qEnergy = qEnergyRepository.findQEnergyBySpaceIdAndDate(spaceId, LocalDateTime.now())
                .orElseThrow(() -> new AppException(ErrorCode.QENERGY_NOT_FOUND));
        qEnergy.getQEnergyId();
        return qEnergyMapper.toQEnergyDTO(qEnergy);
    }

    public BigDecimal getTotalEnergyConsumptionBySpaceIdAndDateRange(QEneryTotalConsumptionDateRangeDTO dto) {
        checkSpaceHavingSiteId(dto.getSpaceId());
        QEnergy fromQEnergy;

        if (dto.getFrom() == null) {
            fromQEnergy = QEnergy.builder().totalEnergyConsumption(ZERO).build();
        } else {
            fromQEnergy = qEnergyRepository
                    .findQEnergyBySpaceIdAndDate(dto.getSpaceId(), dto.getFrom().minusSeconds(30))
                    .orElseGet(() -> QEnergy.builder().totalEnergyConsumption(ZERO).build());
        }

        if (dto.getTo() == null) {
            dto.setTo(MAX_DATE_TIME);
        }


        QEnergy toQEnergy = qEnergyRepository.findQEnergyBySpaceIdAndDate(dto.getSpaceId(), dto.getTo().plusSeconds(30))
                .orElseThrow(() -> new AppException(ErrorCode.NO_QENERGY_AT_SPECIFIC_DATE_RANGE));

        return toQEnergy.getTotalEnergyConsumption().subtract(fromQEnergy.getTotalEnergyConsumption());
    }

    @Scheduled(cron = "0 * * * * *")
    public void getCurrentEnergyConsumptionOfAllSites() {
        cachedSpacesWithBlockType.values().parallelStream().forEach(eachSpace -> {
            try {
                BigDecimal livePower = qenergyUtils.getCurrentEnergyConsumptionBySiteId(eachSpace.getQEnergySiteId())
                        .multiply(ONE_MINUTE_IN_HOUR);
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime timeToGetPrevious = currentTime.minusSeconds(30);

                QEnergy qEnergyLastOneMinute = qEnergyRepository
                        .findQEnergyBySpaceIdAndDate(eachSpace.getSpaceId(), timeToGetPrevious)
                        .orElseGet(() -> QEnergy.builder()
                                .totalEnergyConsumption(ZERO)
                                .build());

                QEnergy qEnergy = QEnergy.builder()
                        .currentEnergyConsumption(livePower)
                        .totalEnergyConsumption(qEnergyLastOneMinute.getTotalEnergyConsumption()
                                .add(livePower))
                        .date(currentTime)
                        .space(eachSpace)
                        .build();

                qEnergyRepository.save(qEnergy);

            } catch (AppException e) {
                System.out.println(
                        "Failed to get live power for site: " + eachSpace.getSpaceName() + " - " + e.getMessage());
            }
        });
    }

    // ktr xem space đó có liên kết vs QEnergy ko
    // nếu ko thì khỏi query
    private void checkSpaceHavingSiteId(UUID spaceId) {
        Space space = cachedSpacesWithBlockType.getOrDefault(spaceId, null);
        if (space == null) {
            Space checkSpace = spaceRepository.findBySpaceIdHavingQEnergySiteId(spaceId)
                    .orElseThrow(() -> new AppException(ErrorCode.SPACE_NOT_FOUND));

            if (checkSpace.getQEnergySiteId() == null || checkSpace.getQEnergySiteId().isEmpty()) {
                throw new AppException(ErrorCode.SITE_ID_NOT_FOUND);
            }
            cachedSpacesWithBlockType.put(checkSpace.getSpaceId(), checkSpace);

        }
    }

    @PostConstruct
    private void initializeCachedSiteIdOfSpaces() {
        List<Space> spacesWithBlockType = spaceRepository.findAllSpacesHavingQEnergySideId();
        for (Space space : spacesWithBlockType) {
            cachedSpacesWithBlockType.put(space.getSpaceId(), space);
        }
    }

}
