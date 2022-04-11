package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.User;
import com.example.booking.service.SignupService;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SignupCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignupCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        String name = request.getParameter(Constants.PARAMETER_NAME).trim();
        String password = request.getParameter(Constants.PARAMETER_PASSWORD).trim();

        Optional<User> optionalUser = SignupService.getInstance().signupLoginPassword(name, password);

        if (optionalUser.isPresent()) {
            LOGGER.info("Successful signing up for user " + name);
            return new LoginCommand().execute(request);
        } else {
            LOGGER.info("Unsuccessful signing up for user " + name);
            request.setAttribute(Constants.SIGNUP_MESSAGE_BAD_CREDENTIALS, true);
            request.setAttribute(Constants.ATTRIBUTE_PREV_NAME, name);
            return new ForwardPage(Constants.SIGNUP_PAGE_URL);
        }
    }

    @Override
    public String toString() {
        return "SignupCommand";
    }
}
