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
import lombok.experimental.FieldDefaults;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier")
    UUID valueId;

    @Column(nullable = false, unique = true)
    String valueName;

    @Builder.Default
    @OneToMany(mappedBy = "value", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EquipmentState> equipmentStates = new ArrayList<>();

}
