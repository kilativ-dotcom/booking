package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.SuitesService;

import javax.servlet.http.HttpServletRequest;

public class ViewSuitesCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        request.setAttribute(Constants.ATTRIBUTE_ALL_SUITES, SuitesService.getAllSuites());
        return new ForwardPage(Constants.SUITES_FOR_ADMIN_PAGE_URL);
    }
}
