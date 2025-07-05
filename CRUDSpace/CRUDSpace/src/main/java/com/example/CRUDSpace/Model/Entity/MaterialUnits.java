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
@Table(name = "material_units", uniqueConstraints = @UniqueConstraint(columnNames = { "space_id", "material_id" }))
public class MaterialUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "material_units_id", columnDefinition = "uniqueidentifier")
    UUID materialUnitsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    Material material;

    @Builder.Default
    int totalUnits = 0;

    @Builder.Default
    int availableUnits = 0;

    @Builder.Default
    int unitsFromChildren = 0;
}
