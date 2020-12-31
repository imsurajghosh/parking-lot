package com.squadstack.parkinglot.models.slots;

import com.squadstack.parkinglot.models.ParkableEntity;

import java.util.Objects;

public class AcquiredSlot extends Slot {
    private final ParkableEntity parkableEntity;

    public AcquiredSlot(ParkableEntity parkableEntity, AvailableSlot availableSlot) {
        super(availableSlot.getSlotNumber());
        this.parkableEntity = parkableEntity;
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
}
