package com.squadstack.parkinglot.models.slots;

import com.squadstack.parkinglot.models.ParkableEntity;
import lombok.Builder;

import java.util.Objects;

public class AcquiredSlot extends Slot {
    private final ParkableEntity parkableEntity;

    public AcquiredSlot(ParkableEntity parkableEntity, AvailableSlot availableSlot) {
        super(availableSlot.getSlotNumber());
        this.parkableEntity = parkableEntity;
    }

    @Builder
    public AcquiredSlot(ParkableEntity parkableEntity, Integer slotNumber) {
        super(slotNumber);
        this.parkableEntity = parkableEntity;
    }

    @Override
    public <T> T accept(SlotVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcquiredSlot that = (AcquiredSlot) o;
        return getSlotNumber() == that.getSlotNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSlotNumber(), parkableEntity);
    }

    public ParkableEntity getParkableEntity() {
        return parkableEntity;
    }

    @Override
    public String toString() {
        return "AcquiredSlot{" +
                "parkableEntity=" + parkableEntity +
                "} " + super.toString();
    }
}
