package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Response;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.ResponsesService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUserResponsesCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(ViewUserResponsesCommand.class);
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        int requestCreatorId = user.getId();
        List<Response> responses = ResponsesService.getInstance().getResponsesForUserRequests(requestCreatorId);
        LOGGER.info(String.format("found %d responses%n", responses.size()));
        request.setAttribute(Constants.ATTRIBUTE_RESPONSES_FOR_USER_REQUESTS, responses);
        return new ForwardPage(Constants.RESPONSES_FOR_USER_PAGE_URL);
    }
}
