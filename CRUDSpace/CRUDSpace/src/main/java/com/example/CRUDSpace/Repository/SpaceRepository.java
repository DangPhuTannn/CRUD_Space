package com.example.CRUDSpace.Repository;

import java.util.Collection;
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
                        select s
                        from Space s
                        join fetch s.type t
                        where s.spaceId = :spaceId
                        """)
        Optional<Space> findBySpaceIdWithType(@Param("spaceId") UUID spaceId);

        @Query("""
                        Select s
                        from Space s
                        where s.qEnergySiteId is not null
                        """)
        List<Space> findAllSpacesHavingQEnergySideId();

        @Query("""
                        select s
                        from Space s
                        where s.spaceId = :spaceId
                        and s.qEnergySiteId is not null
                                    """)
        Optional<Space> findBySpaceIdHavingQEnergySiteId(@Param("spaceId") UUID spaceId);

        @Query("""
                        select s
                        from Space s
                        join fetch s.type t
                        where t.typeId = :typeId
                        """)
        List<Space> findAllSpacesByTypeId(@Param("typeId") UUID typeId);

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

        List<Space> findAllByType_TypeName(String name);

        List<Space> findAllByType_TypeNameIn(Collection<String> names);

        boolean existsBySpaceName(String spaceName);

        @Query("""
                        select s
                        from Space s
                        join s.type t
                        where s.parentId = :spaceId and t.typeName = 'Inventory'
                        """)
        List<Space> findChildrenInventoryByParentSpaceId(@Param("spaceId") UUID spaceId);

        @Query(value = """
                        SELECT
                            inv.space_id AS spaceId,
                            inv.space_name AS spaceName,
                            m.material_id AS materialId,
                            m.material_name AS materialName,
                            mu.available_units AS availableUnits,
                            mt.material_type_id AS materialTypeId,
                            mt.material_type_name AS materialTypeName,
                            mt.measurement AS measurement
                        FROM Space inv
                        INNER JOIN [Type] t ON inv.type_id = t.type_id
                        INNER JOIN Material_Units mu ON mu.space_id = inv.space_id
                        INNER JOIN Material m ON mu.material_id = m.material_id
                        INNER JOIN Material_Type mt ON m.material_type_id = mt.material_type_id
                        WHERE inv.space_id IN (:inventoryIds)
                        """, nativeQuery = true)
        List<Object[]> findMaterialsByInventoryIds(@Param("inventoryIds") List<UUID> inventoryIds);

        @Query(value = """
                        WITH space_ancestors AS (
                            SELECT space_id, parent_id, 0 AS level
                            FROM Space
                            WHERE space_id = :spaceId

                            UNION ALL

                            SELECT s.space_id, s.parent_id, sa.level + 1
                            FROM Space s
                            INNER JOIN space_ancestors sa ON s.space_id = sa.parent_id
                        )
                        SELECT
                            inv.space_id AS spaceId,
                            inv.space_name AS spaceName,
                            m.material_id AS materialId,
                            m.material_name AS materialName,
                            mu.available_units AS availableUnits,
                            mt.material_type_id AS materialTypeId,
                            mt.material_type_name AS materialTypeName,
                            mt.measurement AS measurement
                        FROM Space inv
                        INNER JOIN [Type] t ON inv.type_id = t.type_id
                        INNER JOIN Material_Units mu ON mu.space_id = inv.space_id
                        INNER JOIN Material m ON mu.material_id = m.material_id
                        INNER JOIN Material_Type mt ON m.material_type_id = mt.material_type_id
                        WHERE t.type_name = 'Inventory'
                          AND mu.available_units > 0
                          AND inv.parent_id IN (
                              SELECT space_id FROM space_ancestors
                          )
                        """, nativeQuery = true)
        List<Object[]> findInventoryMaterialsInChildrenOfAncestors(@Param("spaceId") UUID spaceId);

        @Query(value = """
                        WITH ancestors (space_id, parent_id) AS (
                            SELECT space_id, parent_id
                            FROM Space
                            WHERE space_id = :spaceId

                            UNION ALL

                            SELECT s.space_id, s.parent_id
                            FROM Space s
                            INNER JOIN ancestors a ON s.space_id = a.parent_id
                        )
                        SELECT *
                        FROM Space
                        WHERE space_id IN (
                            SELECT space_id
                            FROM ancestors
                        ) and space_id != :spaceId
                        """, nativeQuery = true)
        List<Space> findAllParentSpaces(@Param("spaceId") UUID spaceId);

        @Query("""
                        SELECT s
                        FROM Space s
                        JOIN s.type t
                        WHERE s.parentId IN :spaceIds
                        AND t.typeName = 'Inventory'
                        """)
        List<Space> findAllChildrenInventoryBySpaceIds(@Param("spaceIds") List<UUID> spaceIds);

        @Query(value = """
                        SELECT s.*
                        FROM Space s
                        WHERE s.space_id IN (:spaceIds)
                          AND NOT EXISTS (
                              SELECT 1
                              FROM Material_Units mu
                              WHERE mu.space_id = s.space_id
                                AND mu.material_id = :materialId
                          )
                        """, nativeQuery = true)
        List<Space> findAllSpacesWithoutSpecificMaterial(
                        @Param("spaceIds") List<UUID> spaceIds,
                        @Param("materialId") UUID materialId);
}
