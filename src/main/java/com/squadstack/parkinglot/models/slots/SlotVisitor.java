package com.squadstack.parkinglot.models.slots;

public interface SlotVisitor<T> {
    T visit(AvailableSlot slot);

    T visit(AcquiredSlot slot);
}
