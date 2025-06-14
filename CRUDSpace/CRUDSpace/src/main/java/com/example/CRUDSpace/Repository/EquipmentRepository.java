package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithSpaceDTO;
import com.example.CRUDSpace.Model.Entity.Equipment;
import com.example.CRUDSpace.Model.Entity.Space;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    boolean existsBySpace(Space space);

    @Query("""
            Select new com.example.CRUDSpace.Model.DTO.Equipment.EquipmentWithSpaceDTO(
                e.equipmentId, e.equipmentName,
                new com.example.CRUDSpace.Model.DTO.Space.SpaceDTO(
                    s.spaceId, s.spaceName, s.parentId
                )
            )
            From Equipment e Join e.space s
            """)
    List<EquipmentWithSpaceDTO> getAllEquipmentWithSpace();

}
