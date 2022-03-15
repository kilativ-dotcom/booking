package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.RedirectPage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        if (user != null) {
            LOGGER.info("User " + user.getEmail() + " is logging out");
            request.getSession().invalidate();
        } else {
            LOGGER.warn("Logging out but user is null");
        }
        return new RedirectPage(Constants.INDEX_PAGE_URL);
    }

    @Override
    public String toString() {
        return "LogoutCommand";
    }
}
