package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;
import com.squadstack.parkinglot.models.commands.CommandPattern;

import java.util.List;

@CommandPattern(suffix = "vehicle_registration_number_for_driver_of_age")
public class ListCarPlateNumbersAgainstAgeQueryCommand extends Command {
    private final int age;

    public ListCarPlateNumbersAgainstAgeQueryCommand(int age) {
        this.age = age;
    }

    public ListCarPlateNumbersAgainstAgeQueryCommand (List<String> tokens) {
        this(Integer.valueOf(tokens.get(1)));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public int getAge() {
        return age;
    }
}
