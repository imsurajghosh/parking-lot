package com.squadstack.parkinglot.exception;

public class ParkingLotException extends RuntimeException {

    private ErrorCode errorCode;

    public ParkingLotException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ParkingLotException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingLotException(ErrorCode errorCode, Throwable cause) {
        super(cause);
    }

    public static ParkingLotException from(ErrorCode errorCode, String message) {
        return new ParkingLotException(errorCode, message);
    }

    public static ParkingLotException from(ErrorCode errorCode, Throwable cause) {
        return new ParkingLotException(errorCode, cause);
    }

    public static ParkingLotException from(ErrorCode errorCode, String message, Throwable cause) {
        return new ParkingLotException(errorCode, message, cause);
    }
}
