package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class WrongCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(WrongCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        request.setAttribute(Constants.PARAMETER_WRONG_COMMAND, "wrong command");
        return new ForwardPage(Constants.INDEX_PAGE_URL);
    }

    @Override
    public String toString() {
        return "WrongCommand";
    }
}
