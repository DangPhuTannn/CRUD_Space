package com.example.CRUDSpace.Repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.CRUDSpace.Model.Entity.MaterialType;

public interface MaterialTypeRepository extends JpaRepository<MaterialType, UUID> {

}
