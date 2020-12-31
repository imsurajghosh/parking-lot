package com.squadstack.parkinglot;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import com.squadstack.parkinglot.models.commands.CommandFactory;
import com.squadstack.parkinglot.services.ParkingLotService;
import com.squadstack.parkinglot.services.impl.ParkingLotServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainApplication {
  public static void main(String[] args) {
    Scanner scanner = scannerFromInput(args);
    ParkingLotService parkingLotService = new ParkingLotServiceImpl();
    while (scanner.hasNextLine()) {
      System.out.println(parkingLotService.execute(scanner.nextLine()));
    }
  }

  private static Scanner scannerFromInput(String[] args) {
    int index = Arrays.asList(args).indexOf("-f");
    File input = null;
    if (index == -1) {
      input = new File("input.txt");
    } else {
      input = new File(args[index +1]);
    }
    try{
      Scanner scanner = new Scanner(input);
      return scanner;
    } catch (FileNotFoundException e) {
        throw ParkingLotException.from(ErrorCode.INVALID_FILEPATH_EXCEPTION, "please enter a filename with -f <filename>" +
                " or provide input.txt in the same directory as jar" , e);
    }
  }
}