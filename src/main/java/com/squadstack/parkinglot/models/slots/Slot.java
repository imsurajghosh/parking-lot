package com.squadstack.parkinglot.models.slots;

import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.Objects;

public abstract class Slot {
    private final int slotNumber;

    protected Slot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public abstract <T> T accept(SlotVisitor<T> visitor);

    public int getSlotNumber() {
        return slotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return slotNumber == slot.slotNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotNumber);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "slotNumber=" + slotNumber +
                '}';
    }
}
