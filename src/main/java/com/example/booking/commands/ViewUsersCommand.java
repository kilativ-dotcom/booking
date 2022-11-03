package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ViewUsersCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        request.setAttribute(Constants.ATTRIBUTE_ALL_USERS, UserService.getInstance().getAll());
        return new ForwardPage(Constants.USERS_FOR_ADMIN_PAGE_URL);
    }
}
