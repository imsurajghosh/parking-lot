package com.squadstack.parkinglot.models.commands;

import com.squadstack.parkinglot.exception.ErrorCode;
import com.squadstack.parkinglot.exception.ParkingLotException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

public class CommandFactory {
    private CommandFactory() {
        throw new UnsupportedOperationException();
    }

    public static Map<String, Constructor<? extends Command>> constructors = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.squadstack.parkinglot.models.commands");
        Set<Class<? extends Command>> subTypesOf = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> aClass : subTypesOf) {
            CommandPattern annotation = aClass.getAnnotation(CommandPattern.class);
            Class[] cargs = new Class[1];
            cargs[0] = List.class;
            try {
                constructors.put(annotation.suffix(), aClass.getDeclaredConstructor(cargs));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    }

    public static Command parse(String command) {
        try {
            List<String> tokens = Arrays.asList(command.split(" "));
            return constructors.get(tokens.get(0).toLowerCase()).newInstance(tokens);
        } catch (Exception exception) {
            throw ParkingLotException.from(ErrorCode.INVALID_COMMAND, "invalid command pattern please refer the input " +
                    "manual", exception);
        }
    }
}
