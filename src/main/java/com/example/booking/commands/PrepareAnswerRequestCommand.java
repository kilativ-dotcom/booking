package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Suite;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.SuitesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PrepareAnswerRequestCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        int requestId = Integer.parseInt(request.getParameter(Constants.PARAMETER_PREPARE_ANSWER_REQUEST));
        List<Suite> suites = SuitesService.getPossibleSuitesForRequest(requestId);
        request.setAttribute(Constants.ATTRIBUTE_SUITABLE_SUITES, suites);
        request.setAttribute(Constants.ATTRIBUTE_REQUEST_ID, requestId);
        return new ForwardPage(Constants.SUITABLE_SUITES_PAGE_URL);
    }
}
