package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandPattern;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.Arrays;
import java.util.List;

@CommandPattern(suffix = "park")
public class ParkCommand extends Command {

    private final ParkableEntity parkableEntity;

    public ParkCommand(String carPlateNumber, int age) {
        this.parkableEntity = new ParkableEntity(carPlateNumber, age);
    }

    public ParkCommand (List<String> tokens) {
        this(tokens.get(1), Integer.valueOf(tokens.get(tokens.indexOf("driver_age") + 1)));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public ParkableEntity getParkableEntity() {
        return parkableEntity;
    }
}