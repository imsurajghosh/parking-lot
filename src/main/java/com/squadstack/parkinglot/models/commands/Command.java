package com.squadstack.parkinglot.models.commands;

public abstract class Command {
    abstract public <T> T accept(CommandVisitor<T> commandVisitor);
}
