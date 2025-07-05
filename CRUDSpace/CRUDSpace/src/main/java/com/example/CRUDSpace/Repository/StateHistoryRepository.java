package com.example.CRUDSpace.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRUDSpace.Model.Entity.StateHistory;

public interface StateHistoryRepository extends JpaRepository<StateHistory, UUID> {

}
