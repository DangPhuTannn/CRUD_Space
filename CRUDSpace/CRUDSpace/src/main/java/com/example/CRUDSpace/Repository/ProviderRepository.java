package com.example.CRUDSpace.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRUDSpace.Model.Entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    Optional<Provider> findByProviderName(String providerName);
}
