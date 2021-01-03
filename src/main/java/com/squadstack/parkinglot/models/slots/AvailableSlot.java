package com.squadstack.parkinglot.models.slots;

import lombok.Builder;

import java.util.Objects;

public class AvailableSlot extends Slot {

    @Builder
    public AvailableSlot(int slotNumber) {
        super(slotNumber);
    }

    @Override
    public <T> T accept(SlotVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableSlot that = (AvailableSlot) o;
        return getSlotNumber() == that.getSlotNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSlotNumber());
    }

    @Override
    public String toString() {
        return "AvailableSlot{} " + super.toString();
    }
}
