package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Request;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.RequestsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class StartAnswerRequestCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {

        List<Request> requests = RequestsService.getUnansweredRequests();
        request.setAttribute(Constants.ATTRIBUTE_UNANSWERED_REQUESTS, requests);
        return new ForwardPage(Constants.UNANSWERED_REQUESTS_PAGE_URL);
    }
}
