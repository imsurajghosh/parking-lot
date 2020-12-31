package com.squadstack.parkinglot.models.commands;

import com.squadstack.parkinglot.models.commands.impl.*;

public interface CommandVisitor<T> {
    T visit(CreateParkingLotCommand command);

    T visit(ParkCommand command);

    T visit(ListSlotNumbersAgainstAgeQueryCommand command);

    T visit(GetSlotNumberAgainstCarPlateQueryCommand command);

    T visit(LeaveParkingLotCommand command);

    T visit(ListCarPlateNumbersAgainstAgeQueryCommand command);
}
