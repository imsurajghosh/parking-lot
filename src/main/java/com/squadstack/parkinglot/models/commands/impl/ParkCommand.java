package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.ParkableEntity;
import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.Arrays;

public class ParkCommand extends Command {

    private final ParkableEntity parkableEntity;

    public ParkCommand(String carPlateNumber, int age) {
        this.parkableEntity = new ParkableEntity(carPlateNumber, age);
    }

    public static ParkCommand construct(String[] tokens) {
        int index = Arrays.asList(tokens).indexOf("driver_age");
        return new ParkCommand(tokens[1], Integer.valueOf(tokens[index+1]));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public ParkableEntity getParkableEntity() {
        return parkableEntity;
    }
}