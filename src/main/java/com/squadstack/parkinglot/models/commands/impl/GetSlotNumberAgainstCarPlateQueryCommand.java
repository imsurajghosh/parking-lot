package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

public class GetSlotNumberAgainstCarPlateQueryCommand implements Command {

    private final String carPlateNumber;

    public GetSlotNumberAgainstCarPlateQueryCommand(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public static GetSlotNumberAgainstCarPlateQueryCommand construct(String[] tokens) {
        return new GetSlotNumberAgainstCarPlateQueryCommand(tokens[1]);
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }
}
