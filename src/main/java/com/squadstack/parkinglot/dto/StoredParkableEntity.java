package com.squadstack.parkinglot.dto;

import com.squadstack.parkinglot.models.slots.Slot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "parkable",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "car_plate_number")},
        indexes = {
                @Index(name = "car_plate_number_index", columnList = "car_plate_number"),
                @Index(name = "age_index", columnList = "age"),
        })
public class StoredParkableEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993194676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "car_plate_number", unique = true, nullable = false, length = 100)
    private String carPlateNumber;

    @Column(name = "age", unique = false, nullable = false, length = 100)
    private Integer age;

    @OneToOne(mappedBy="storedParkableEntity")
    private StoredSlot slot;
}