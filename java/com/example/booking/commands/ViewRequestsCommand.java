package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Request;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.RequestsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewRequestsCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        List<Request> all = RequestsService.getInstance().getAll();
        request.setAttribute(Constants.ATTRIBUTE_ALL_REQUESTS, all);
        return new ForwardPage(Constants.REQUESTS_FOR_ADMIN_PAGE_URL);
    }
}
