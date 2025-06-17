package com.example.CRUDSpace.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRUDSpace.Model.Entity.Value;

public interface ValueRepository extends JpaRepository<Value, UUID> {
    Optional<Value> findByValueName(String valueName);
}
