package com.squadstack.parkinglot.models.slots;

public interface SlotStateVisitor<T> {
    T visitAcquired();

    T visitAvailable();
}