package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.pages.RedirectPage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class WrongCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(WrongCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        request.setAttribute(Constants.PARAMETER_WRONG_COMMAND, "wrong command");
        return new RedirectPage(Constants.INDEX_PAGE_URL);
    }

    @Override
    public String toString() {
        return "WrongCommand";
    }
}
