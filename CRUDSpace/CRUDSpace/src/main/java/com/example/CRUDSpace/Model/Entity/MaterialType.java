package com.example.CRUDSpace.Model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier")
    UUID materialTypeId;

    @Column(nullable = false, unique = true)
    String materialTypeName;

    @Column(nullable = false)
    String measurement;

    @Builder.Default
    @OneToMany(mappedBy = "materialType", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Material> materials = new ArrayList<>();
}
