package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Response;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.ResponsesService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SelectSuiteForRequestCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        int requestId = Integer.parseInt(request.getParameter(Constants.PARAMETER_REQUEST_FOR_SUITE));
        int suiteId = Integer.parseInt(request.getParameter(Constants.PARAMETER_SUITE_FOR_REQUEST));
        int creatorId = ((User)request.getSession().getAttribute(Constants.ATTRIBUTE_USER)).getId();
        Optional<Response> optionalResponse = ResponsesService.getInstance().createResponse(suiteId, creatorId, requestId);
        if (optionalResponse.isPresent()){
            request.setAttribute(Constants.ATTRIBUTE_CREATED_RESPONSE, optionalResponse.get());
        }
        return new ForwardPage(Constants.WELCOME_PAGE_URL);
    }
}
