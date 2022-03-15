package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;

import javax.servlet.http.HttpServletRequest;

public class PrepareLoginCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        return new ForwardPage(Constants.LOGIN_PAGE_URL);
    }
}
