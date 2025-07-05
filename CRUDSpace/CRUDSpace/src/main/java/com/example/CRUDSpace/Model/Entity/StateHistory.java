package com.example.CRUDSpace.Model.Entity;

import java.util.UUID;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "state_history_id", columnDefinition = "uniqueidentifier")
    UUID stateHistoryId;

    @Column(nullable = false)
    String deviceId;

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
