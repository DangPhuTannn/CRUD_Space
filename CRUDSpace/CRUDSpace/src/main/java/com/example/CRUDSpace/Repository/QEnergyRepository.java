package com.example.CRUDSpace.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CRUDSpace.Model.Entity.QEnergy;

public interface QEnergyRepository extends JpaRepository<QEnergy, UUID> {

    @Query("""
            select q
            from QEnergy q
            where q.space.spaceId = :spaceId
            and q.date <= :date
            order by q.date desc
            limit 1
            """)
    Optional<QEnergy> findQEnergyBySpaceIdAndDate(
            @Param("spaceId") UUID spaceId,
            @Param("date") LocalDateTime date);

}
