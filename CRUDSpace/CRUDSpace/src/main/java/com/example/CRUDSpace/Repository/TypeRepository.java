package com.example.CRUDSpace.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRUDSpace.Model.Entity.Type;

public interface TypeRepository extends JpaRepository<Type, UUID> {

    boolean existsByLevel(int level);

    boolean existsByTypeName(String typeName);

}
