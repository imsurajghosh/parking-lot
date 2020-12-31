package com.squadstack.parkinglot.models.commands;

public interface Command {
    <T> T accept(CommandVisitor<T> commandVisitor);
}
