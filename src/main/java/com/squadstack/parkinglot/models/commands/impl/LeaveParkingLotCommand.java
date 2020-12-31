package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

public class LeaveParkingLotCommand implements Command {

    private final int slotNumber;

    public LeaveParkingLotCommand(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public static LeaveParkingLotCommand construct(String[] tokens) {
        return new LeaveParkingLotCommand(Integer.valueOf(tokens[1]));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
