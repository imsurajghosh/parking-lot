package com.squadstack.parkinglot.utils;

import com.squadstack.parkinglot.dto.StoredParkableEntity;
import com.squadstack.parkinglot.dto.StoredSlot;
import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.slots.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperUtils {

    private MapperUtils() {
    }

    public static StoredSlot toStoredSlot(Slot slot) {
        return slot.accept(new SlotVisitor<StoredSlot>() {
            @Override
            public StoredSlot visit(AvailableSlot slot) {
                return StoredSlot.builder()
                        .state(SlotState.AVAILABLE)
                        .slotNumber(slot.getSlotNumber())
                        .build();
            }

            @Override
            public StoredSlot visit(AcquiredSlot slot) {
                return StoredSlot.builder()
                        .state(SlotState.ACQUIRED)
                        .slotNumber(slot.getSlotNumber())
                        .storedParkableEntity(toStoredParkableEntity(slot.getParkableEntity()))
                        .build();
            }
        });
    }

    public static StoredParkableEntity toStoredParkableEntity(ParkableEntity parkableEntity) {
        return StoredParkableEntity.builder()
                .age(parkableEntity.getAge())
                .carPlateNumber(parkableEntity.getCarPlateNumber())
                .build();
    }

    public static ParkableEntity toParkableEntity(StoredParkableEntity storedParkableEntity) {
        return ParkableEntity.builder()
                .age(storedParkableEntity.getAge())
                .carPlateNumber(storedParkableEntity.getCarPlateNumber())
                .build();
    }

    public static AvailableSlot toAvailableSlot(Slot slot) {
        if (slot instanceof AvailableSlot)
            return (AvailableSlot) slot;

        log.error("tried to cast %s to availableSlot", slot.toString());
        throw ParkingLotException.from(ErrorCode.INVALID_CAST, "cast to invalid type");
    }

    public static AcquiredSlot toAcquiredSlot(Slot slot) {
        if (slot instanceof AcquiredSlot)
            return (AcquiredSlot) slot;

        log.error("tried to cast %s to acquiredSlot", slot.toString());
        throw ParkingLotException.from(ErrorCode.INVALID_CAST, "cast to invalid type");
    }

    public static Slot toSlot(StoredSlot storedSlot) {
        return storedSlot.getState().accept(new SlotStateVisitor<Slot>() {
            @Override
            public AcquiredSlot visitAcquired() {
                return AcquiredSlot.builder()
                        .slotNumber(storedSlot.getSlotNumber())
                        .parkableEntity(toParkableEntity(storedSlot.getStoredParkableEntity()))
                        .build();
            }

            @Override
            public AvailableSlot visitAvailable() {
                return AvailableSlot.builder()
                        .slotNumber(storedSlot.getSlotNumber())
                        .build();
            }
        });
    }
}