package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Request;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.RequestsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUserRequestsCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        List<Request> requests = RequestsService.getInstance().getRequestsFromUser(user.getId());
        request.setAttribute(Constants.ATTRIBUTE_REQUESTS_FOR_USER, requests);
        return new ForwardPage(Constants.REQUESTS_FOR_USER_PAGE_URL);
    }
}
