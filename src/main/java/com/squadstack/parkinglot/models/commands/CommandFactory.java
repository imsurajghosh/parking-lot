package com.squadstack.parkinglot.models.commands;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.commands.impl.*;

public class CommandFactory {
    private CommandFactory() {
        throw new UnsupportedOperationException();
    }

    public static Command parse(String command) {
        try {
            String[] tokens = command.split(" ");
            if ("create_parking_lot".equalsIgnoreCase(tokens[0])) {
                return CreateParkingLotCommand.construct(tokens);
            }
            if ("park".equalsIgnoreCase(tokens[0])) {
                return ParkCommand.construct(tokens);
            }
            if ("slot_numbers_for_driver_of_age".equalsIgnoreCase(tokens[0])) {
                return ListSlotNumbersAgainstAgeQueryCommand.construct(tokens);
            }
            if ("slot_number_for_car_with_number".equalsIgnoreCase(tokens[0])) {
                return GetSlotNumberAgainstCarPlateQueryCommand.construct(tokens);
            }
            if ("leave".equalsIgnoreCase(tokens[0])) {
                return LeaveParkingLotCommand.construct(tokens);
            }
            if ("vehicle_registration_number_for_driver_of_age".equalsIgnoreCase(tokens[0])) {
                return ListCarPlateNumbersAgainstAgeQueryCommand.construct(tokens);
            }
        } catch (Exception exception) {
            throw ParkingLotException.from(ErrorCode.INVALID_COMMAND, "invalid command pattern please refer the input " +
                    "manual", exception);
        }
        throw ParkingLotException.from(ErrorCode.INVALID_COMMAND, "invalid command pattern please refer the input " +
                "manual");
    }
}
