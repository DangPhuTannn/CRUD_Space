package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithAllRelationsDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;
import com.example.CRUDSpace.Model.Entity.Space;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    boolean existsBySpace(Space space);

    @Query("""
            Select new com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithAllRelationsDTO(
                e.equipmentId, e.equipmentName,
                new com.example.CRUDSpace.Model.DTO.Space.SpaceDTO(
                    s.spaceId, s.spaceName, s.parentId
                ),
                new com.example.CRUDSpace.Model.DTO.Provider.ProviderDTO(
                p.providerId,p.providerName
                )
            )
            From Equipment e
            Join e.space s
            Join e.provider p
            """)
    List<EquipmentWithAllRelationsDTO> getAllEquipmentWithSpace();

    @Query("""
            Select e
            from Equipment e
            where e.deviceId = :deviceId
            """)
    Optional<Equipment> findByDeviceId(@Param("deviceId") String deviceId);

    long countBySpace(Space space);
}
