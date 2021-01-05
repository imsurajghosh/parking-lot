package com.squadstack.parkinglot.models.commands.impl;

import com.squadstack.parkinglot.models.commands.Command;
import com.squadstack.parkinglot.models.commands.CommandPattern;
import com.squadstack.parkinglot.models.commands.CommandVisitor;

import java.util.List;

@CommandPattern(suffix = "slot_numbers_for_driver_of_age")
public class ListSlotNumbersAgainstAgeQueryCommand extends Command {

    private final int age;

    public ListSlotNumbersAgainstAgeQueryCommand(int age) {
        this.age = age;
    }

    public ListSlotNumbersAgainstAgeQueryCommand (List<String> tokens) {
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
