package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandPattern;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.List;

@CommandPattern(suffix = "slot_number_for_car_with_number")
public class GetSlotNumberAgainstCarPlateQueryCommand extends Command {

    private final String carPlateNumber;

    public GetSlotNumberAgainstCarPlateQueryCommand(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public GetSlotNumberAgainstCarPlateQueryCommand (List<String> tokens) {
        this(tokens.get(1));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }
}
