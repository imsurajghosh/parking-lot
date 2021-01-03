package com.squadstack.parkinglot.repository.impl;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.models.slots.AvailableSlot;
import com.squadstack.parkinglot.models.slots.Slot;
import com.squadstack.parkinglot.models.slots.SlotVisitor;
import com.squadstack.parkinglot.repository.SlotStorageRepository;
import com.squadstack.parkinglot.utils.ValidationUtils;

import java.util.*;

import static com.squadstack.parkinglot.constants.Constants.AGE_LIMIT;

public class InMemorySlotStorageRepositoryImpl implements SlotStorageRepository {

    private final PriorityQueue<AvailableSlot> availableSlots;
    private final List<AcquiredSlot>[] acquiredSlotsWithAge = new ArrayList[AGE_LIMIT];
    private Slot[] allSlots;
    private final Map<String, AcquiredSlot> carPlateToSlotMapping;
    private final int slotSize;

    public InMemorySlotStorageRepositoryImpl(int n) {

        // initialize with empty collections
        for (int i = 0; i < acquiredSlotsWithAge.length; ++i) {
            acquiredSlotsWithAge[i] = new ArrayList<>();
        }
        carPlateToSlotMapping = new HashMap<>();
        PriorityQueue<AvailableSlot> priorityQueue =
                new PriorityQueue<>((o1 ,o2) -> Integer.compare(o1.getSlotNumber(),o2.getSlotNumber()));
        Slot[] allSlots = new Slot[n+1];

        slotSize = n;
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
        ValidationUtils.ageCheck(age);
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
        ValidationUtils.ageCheck(parkableEntity.getAge());
        AvailableSlot nextAvailableSlot = getNextAvailableSlot();
        AcquiredSlot acquiredSlot = new AcquiredSlot(parkableEntity, nextAvailableSlot);
        acquiredSlotsWithAge[parkableEntity.getAge()].add(acquiredSlot);
        carPlateToSlotMapping.put(acquiredSlot.getParkableEntity().getCarPlateNumber(), acquiredSlot);
        allSlots[acquiredSlot.getSlotNumber()] = acquiredSlot;
        return acquiredSlot;
    }

    @Override
    public AcquiredSlot leaveSlot(int slotNumber) {
        if (slotNumber < 1 || slotNumber > slotSize ) {
            throw ParkingLotException.from(ErrorCode.INVALID_SLOT_NUMBER, "slot number not within range");
        }
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
                        "Cannot leave unacquired slot");
            }
        });

        return (AcquiredSlot) leftSlot;
    }

    @Override
    public void close() {
        // do nothing
    }
}
