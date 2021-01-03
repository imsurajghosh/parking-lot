package com.squadstack.parkinglot.dto;

import com.squadstack.parkinglot.models.slots.SlotState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "slot",
    uniqueConstraints = {@UniqueConstraint(columnNames = "slotNumber")},
    indexes = {
      @Index(name = "slot_state_slotnumber_index", columnList = "state, slotNumber"),
    })
public class StoredSlot implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "slotNumber", unique = true, nullable = false)
    private Integer slotNumber;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private SlotState state;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stored_parkable_id", referencedColumnName = "id")
    private StoredParkableEntity storedParkableEntity;
}