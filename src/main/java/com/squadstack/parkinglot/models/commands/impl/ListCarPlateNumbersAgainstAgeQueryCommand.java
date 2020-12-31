package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

public class ListCarPlateNumbersAgainstAgeQueryCommand implements Command {
    private final int age;

    public ListCarPlateNumbersAgainstAgeQueryCommand(int age) {
        this.age = age;
    }

    public static ListCarPlateNumbersAgainstAgeQueryCommand construct(String[] tokens) {
        return new ListCarPlateNumbersAgainstAgeQueryCommand(Integer.valueOf(tokens[1]));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public int getAge() {
        return age;
    }
}
