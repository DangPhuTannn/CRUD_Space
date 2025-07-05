package com.example.CRUDSpace.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.Entity.Value;

public interface ValueRepository extends JpaRepository<Value, UUID> {
    Optional<Value> findByValueName(String valueName);

    @Query("""
                    Select v
                    From Value v
                    Where v.valueName in :valueNames
            """)
    List<Value> findAllByValueNameIn(@Param("valueNames") Set<String> codes);
}
