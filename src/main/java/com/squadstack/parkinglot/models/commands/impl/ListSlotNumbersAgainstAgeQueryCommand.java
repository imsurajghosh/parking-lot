package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

public class ListSlotNumbersAgainstAgeQueryCommand implements Command {

    private final int age;

    public ListSlotNumbersAgainstAgeQueryCommand(int age) {
        this.age = age;
    }

    public static ListSlotNumbersAgainstAgeQueryCommand construct(String[] tokens) {
        return new ListSlotNumbersAgainstAgeQueryCommand(Integer.valueOf(tokens[1]));
    }

    @Override
    public <T> T accept(CommandVisitor<T> commandVisitor) {
        return commandVisitor.visit(this);
    }

    public int getAge() {
        return age;
    }
}
