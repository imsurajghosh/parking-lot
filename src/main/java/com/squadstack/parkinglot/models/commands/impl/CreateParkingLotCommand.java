package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

public class CreateParkingLotCommand implements Command {

    private final int size;

    public CreateParkingLotCommand(int size) {
        this.size = size;
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public static CreateParkingLotCommand construct(String[] tokens) {
        return new CreateParkingLotCommand(Integer.valueOf(tokens[1]));
    }

    public int getSize() {
        return size;
    }
}
