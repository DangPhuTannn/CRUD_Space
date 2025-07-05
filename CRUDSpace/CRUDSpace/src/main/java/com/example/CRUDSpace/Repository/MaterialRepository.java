package com.example.CRUDSpace.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.Entity.Material;

public interface MaterialRepository extends JpaRepository<Material, UUID> {

    @Query("""
                select count(m) > 0
                from Material m
                join m.materialType mt
                where m.materialName = :materialName and mt.materialTypeId = :materialTypeId
            """)
    boolean existsByMaterialNameAndMaterialTypeId(@Param("materialName") String materialName,
            @Param("materialTypeId") UUID materialTypeId);

    @Query("""
            select m
            from Material m
            where m.materialId = :materialId
            """)
    Optional<Material> findByMaterialId(@Param("materialId") UUID materialId);
}
