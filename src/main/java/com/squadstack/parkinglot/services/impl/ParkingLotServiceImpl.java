package com.squadstack.parkinglot.services.impl;

import com.squadstack.parkinglot.constants.ParkingLotConstants;
import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.commands.CommandFactory;
import com.squadstack.parkinglot.models.commands.CommandVisitor;
import com.squadstack.parkinglot.models.commands.impl.*;
import com.squadstack.parkinglot.models.slots.AcquiredSlot;
import com.squadstack.parkinglot.repository.SlotStorageRepository;
import com.squadstack.parkinglot.repository.impl.DatabaseSlotStorageRepositoryImpl;
import com.squadstack.parkinglot.services.ParkingLotService;

import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotServiceImpl implements CommandVisitor<String>, ParkingLotService {

    private SlotStorageRepository slotStorageRepository;

    @Override
    public String execute(String command) {
        return CommandFactory.parse(command).accept(this);
    }

    @Override
    public void close() {
        slotStorageRepository.close();
    }

    @Override
    public String visit(CreateParkingLotCommand command) {
        slotStorageRepository = new DatabaseSlotStorageRepositoryImpl(command.getSize());
        return String.format(ParkingLotConstants.CREATED, command.getSize());
    }

    @Override
    public String visit(ParkCommand command) {
        AcquiredSlot acquiredSlot = slotStorageRepository.acquireSlot(command.getParkableEntity());
        return String.format(ParkingLotConstants.PARKED, acquiredSlot.getParkableEntity().getCarPlateNumber(),
                acquiredSlot.getSlotNumber());
    }

    @Override
    public String visit(ListSlotNumbersAgainstAgeQueryCommand command) {
        List<AcquiredSlot> acquiredSlots = slotStorageRepository.getAcquiredSlot(command.getAge());
        return acquiredSlots.stream()
                .map(AcquiredSlot::getSlotNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public String visit(GetSlotNumberAgainstCarPlateQueryCommand command) {
        AcquiredSlot acquiredSlot = slotStorageRepository.getAcquiredSlot(command.getCarPlateNumber());
        return String.valueOf(acquiredSlot.getSlotNumber());
    }

    @Override
    public String visit(LeaveParkingLotCommand command) {
        AcquiredSlot acquiredSlot = slotStorageRepository.leaveSlot(command.getSlotNumber());
        return String.format(ParkingLotConstants.LEFT, acquiredSlot.getSlotNumber(),
                acquiredSlot.getParkableEntity().getCarPlateNumber(),
                acquiredSlot.getParkableEntity().getAge());
    }

    @Override
    public String visit(ListCarPlateNumbersAgainstAgeQueryCommand command) {
        List<AcquiredSlot> acquiredSlots = slotStorageRepository.getAcquiredSlot(command.getAge());
        return acquiredSlots.stream()
                .map(AcquiredSlot::getParkableEntity)
                .map(ParkableEntity::getCarPlateNumber)
                .collect(Collectors.joining(","));
    }
}
