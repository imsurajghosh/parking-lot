package com.squadstack.parkinglot.repository;

import com.squadstack.parkinglot.dto.StoredSlot;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.models.slots.Slot;

import java.util.List;
import java.util.function.UnaryOperator;

public interface SlotStorageRepository {

    List<Slot> search(Slot slot);

    Slot get(int slotNumber);

    Slot remove(int slotNumber);

    Slot update(Slot slot);

    Slot update(int slotNumber, UnaryOperator<StoredSlot> updateOperation);

    Slot create(Slot slot);
    // search
    List<AcquiredSlot> getAcquiredSlot(int age);

    // search
    AcquiredSlot getAcquiredSlot(String carPlateNumber);

    // get a free slot and update it
    AcquiredSlot acquireSlot(ParkableEntity parkableEntity);

    // remove a slot
    AcquiredSlot leaveSlot(int slotNumber);

    void close();
}
