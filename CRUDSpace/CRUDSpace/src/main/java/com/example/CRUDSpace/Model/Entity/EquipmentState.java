package com.example.CRUDSpace.Model.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "equipment_state", uniqueConstraints = @UniqueConstraint(columnNames = { "equipment_id", "value_id" }))
public class EquipmentState {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "equipment_state_id", columnDefinition = "uniqueidentifier")
    UUID equipmentStateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id", nullable = false)
    Value value;

    @Column(nullable = false)
    String timeStamp;

    @Column(nullable = false)
    String valueResponse;

}
