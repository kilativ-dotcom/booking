package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.User;
import com.example.booking.service.LoginService;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        String name = request.getParameter(Constants.PARAMETER_NAME).trim();
        String password = request.getParameter(Constants.PARAMETER_PASSWORD).trim();

        Optional<User> optionalUser = LoginService.checkLoginPassword(name, password);
        if (optionalUser.isPresent()) {
            LOGGER.info("Successful logging in for user " + name);

            HttpSession session = request.getSession();
            synchronized (session) {
                User previousUser = (User) session.getAttribute(Constants.ATTRIBUTE_USER);
                if (previousUser == null) {
                    session.setAttribute(Constants.ATTRIBUTE_USER, optionalUser.get());
                } else {
                    LOGGER.warn("While logging session already has a user");
                }
            }
            return new ForwardPage(Constants.WELCOME_PAGE_URL);
        } else {
            LOGGER.info("Unsuccessful logging in for user " + name);
            request.setAttribute(Constants.LOGIN_PAGE_MESSAGE, "bad login or password");
            request.setAttribute(Constants.ATTRIBUTE_PREV_NAME, name);
            return new ForwardPage(Constants.LOGIN_PAGE_URL);
        }
    }

    @Override
    public String toString() {
        return "LoginCommand";
    }
}
