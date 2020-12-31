package com.squadstack.parkinglot.repository.impl;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.models.slots.AvailableSlot;
import com.squadstack.parkinglot.models.slots.Slot;
import com.squadstack.parkinglot.models.slots.SlotVisitor;
import com.squadstack.parkinglot.repository.SlotStorageRepository;

import java.util.*;

public class InMemorySlotStorageRepositoryImpl implements SlotStorageRepository {

    private static int AGE_LIMIT = 1005;

    private final PriorityQueue<AvailableSlot> availableSlots;
    private final List<AcquiredSlot>[] acquiredSlotsWithAge = new ArrayList[AGE_LIMIT];
    private Slot[] allSlots;
    private final Map<String, AcquiredSlot> carPlateToSlotMapping;

    public InMemorySlotStorageRepositoryImpl(int n) {

        // initialize with empty collections
        for (int i = 0; i < acquiredSlotsWithAge.length; ++i) {
            acquiredSlotsWithAge[i] = new ArrayList<>();
        }
        carPlateToSlotMapping = new HashMap<>();
        PriorityQueue<AvailableSlot> priorityQueue =
                new PriorityQueue<>((o1 ,o2) -> Integer.compare(o1.getSlotNumber(),o2.getSlotNumber()));
        Slot[] allSlots = new Slot[n+1];

        // populate with all slot as available
        for (int i = 1; i <= n; ++i) {
            AvailableSlot availableSlot = new AvailableSlot(i);
            allSlots[i] = availableSlot;
            priorityQueue.add(availableSlot);
        }
        this.allSlots = allSlots;
        this.availableSlots = priorityQueue;
    }

    private AvailableSlot getNextAvailableSlot() {
        AvailableSlot head = availableSlots.poll();
        if (null == head) {
            throw ParkingLotException.from(ErrorCode.SLOTS_UNAVAILABLE_EXCEPTION, "slots unavailable");
        }
        return head;
    }

    @Override
    public List<AcquiredSlot> getAcquiredSlot(int age) {
        if (age < 1 || age >= AGE_LIMIT) {
            throw ParkingLotException.from(ErrorCode.INVALID_AGE, "Age invalid");
        }
        return acquiredSlotsWithAge[age];
    }

    @Override
    public AcquiredSlot getAcquiredSlot(String carPlateNumber) {
        AcquiredSlot acquiredSlot = carPlateToSlotMapping.get(carPlateNumber);
        if (null == acquiredSlot) {
            throw ParkingLotException.from(ErrorCode.CAR_PLATE_NOT_PRESENT,
                    "car plate number not present in parking lot");
        }
        return acquiredSlot;
    }

    @Override
    public AcquiredSlot acquireSlot(ParkableEntity parkableEntity) {
        AvailableSlot nextAvailableSlot = getNextAvailableSlot();
        AcquiredSlot acquiredSlot = new AcquiredSlot(parkableEntity, nextAvailableSlot);
        acquiredSlotsWithAge[parkableEntity.getAge()].add(acquiredSlot);
        carPlateToSlotMapping.put(acquiredSlot.getParkableEntity().getCarPlateNumber(), acquiredSlot);
        allSlots[acquiredSlot.getSlotNumber()] = acquiredSlot;
        return acquiredSlot;
    }

    @Override
    public AcquiredSlot leaveSlot(int slotNumber) {
        Slot curSlot = allSlots[slotNumber];
        Slot leftSlot = curSlot.accept(new SlotVisitor<Slot>() {
            @Override
            public Slot visit(AcquiredSlot slot) {
                AvailableSlot availableSlot = new AvailableSlot(curSlot.getSlotNumber());
                acquiredSlotsWithAge[((AcquiredSlot) curSlot).getParkableEntity().getAge()].remove(curSlot);
                carPlateToSlotMapping.remove(((AcquiredSlot) curSlot).getParkableEntity().getCarPlateNumber());
                allSlots[curSlot.getSlotNumber()] = availableSlot;
                availableSlots.add(availableSlot);
                return curSlot;
            }

            @Override
            public Slot visit(AvailableSlot slot) {
                throw ParkingLotException.from(ErrorCode.ATTEMPT_TO_LEAVE_UNACQUIRED_SLOT,
                        "cannot leave unacquired slot");
            }
        });

        return (AcquiredSlot) leftSlot;
    }
}
