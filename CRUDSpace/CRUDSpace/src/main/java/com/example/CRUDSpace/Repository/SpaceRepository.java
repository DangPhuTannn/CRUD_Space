package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO;
import com.example.CRUDSpace.Model.Entity.Space;

public interface SpaceRepository extends JpaRepository<Space, UUID> {

    @Query("""
                    Select new com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO(
                        s.spaceId, s.spaceName, s.parentId,
                        new com.example.CRUDSpace.Model.DTO.Type.TypeDTO(
                            t.typeId, t.level, t.typeName
                        )
                    )
                    From Space s Join s.type t
            """)
    List<SpaceWithTypeDTO> getAllSpaceWithTypeDTO();

    @Query("""
                    Select new com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO(
                        s.spaceId, s.spaceName, s.parentId,
                        new com.example.CRUDSpace.Model.DTO.Type.TypeDTO(
                            t.typeId, t.level, t.typeName
                        )
                    )
                    From Space s Join s.type t
                    Where (:spaceId = s.spaceId)
            """)
    Optional<SpaceWithTypeDTO> findSpaceWithTypeDTOById(@Param("spaceId") UUID spaceId);

}
