package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandPattern;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.List;

@CommandPattern(suffix = "leave")
public class LeaveParkingLotCommand extends Command {

    private final int slotNumber;

    public LeaveParkingLotCommand(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public LeaveParkingLotCommand (List<String> tokens) {
        this(Integer.valueOf(tokens.get(1)));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
