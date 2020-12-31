package com.squadstack.parkinglot.utils;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;

import static com.squadstack.parkinglot.constants.Constants.AGE_LIMIT;

public class ValidationUtils {

  public static void ageCheck(int age) {
      if (age < 1 || age > AGE_LIMIT) {
          throw ParkingLotException.from(ErrorCode.INVALID_AGE, "Age invalid");
      }
  }
}