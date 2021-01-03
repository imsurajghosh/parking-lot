package com.squadstack.parkinglot.repository;

import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.models.slots.AvailableSlot;

import java.util.List;

public interface SlotStorageRepository {

    List<AcquiredSlot> getAcquiredSlot(int age);

    AcquiredSlot getAcquiredSlot(String carPlateNumber);

    AcquiredSlot acquireSlot(ParkableEntity parkableEntity);

    AcquiredSlot leaveSlot(int slotNumber);

    void close();
}
