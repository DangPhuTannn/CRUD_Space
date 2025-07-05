package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.Entity.MaterialUnits;

public interface MaterialUnitsRepository extends JpaRepository<MaterialUnits, UUID> {

        @Query("""
                        Select mu
                        from MaterialUnits mu
                        where mu.material.materialId = :materialId and mu.space.spaceId = :spaceId
                        """)
        Optional<MaterialUnits> findByMaterialIdAndSpaceId(@Param("materialId") UUID materialId,
                        @Param("spaceId") UUID spaceId);

        @Query("""
                        Select mu
                        from MaterialUnits mu
                        where mu.space.spaceId = :spaceId
                        """)
        List<MaterialUnits> findAllBySpaceId(@Param("spaceId") UUID spaceId);

        @Query("""
                        Select mu
                        from MaterialUnits mu
                        where mu.space.spaceId in :spaceIds
                        """)
        List<MaterialUnits> findAllBySpaceIds(@Param("spaceIds") List<UUID> spaceIds);

        @Modifying
        @Query("""
                        UPDATE MaterialUnits mu
                        SET mu.unitsFromChildren = mu.unitsFromChildren + :quantity
                        WHERE mu.material.materialId = :materialId
                          AND mu.space.spaceId IN :spaceIds
                        """)
        void incrementUnitsFromChildren(
                        @Param("materialId") UUID materialId,
                        @Param("spaceIds") List<UUID> spaceIds,
                        @Param("quantity") int quantity);

        @Modifying
        @Query("""
                        update MaterialUnits mu
                        set mu.availableUnits = mu.availableUnits + :quantity
                        , mu.totalUnits = mu.totalUnits + :quantity
                        where mu.materialUnitsId = :materialUnitsId
                        """)
        void incrementAvailableAndTotalUnits(
                        @Param("materialUnitsId") UUID materialUnitsId,
                        @Param("quantity") int quantity);
}
