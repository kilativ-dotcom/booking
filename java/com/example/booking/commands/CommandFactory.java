package com.example.booking.commands;

import org.apache.log4j.Logger;

public class CommandFactory {
    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

    public static Command getCommand(String commandParameter) {
        LOGGER.trace("command:" + commandParameter);
        if (commandParameter != null && !commandParameter.isEmpty()) {
            try {
                return CommandEnum.valueOf(commandParameter.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                return new WrongCommand();
            }
        } else {
            return new WrongCommand();
        }
    }
}
