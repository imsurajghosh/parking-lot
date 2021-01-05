package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandPattern;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.List;

@CommandPattern(suffix = "create_parking_lot")
public class CreateParkingLotCommand extends Command {

    private final int size;

    public CreateParkingLotCommand(int size) {
        this.size = size;
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public CreateParkingLotCommand (List<String> tokens) {
        this(Integer.valueOf(tokens.get(1)));
    }

    public int getSize() {
        return size;
    }
}
