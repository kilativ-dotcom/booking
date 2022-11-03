package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Request;
import com.example.booking.entity.Suite;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.RequestsService;
import com.example.booking.service.SuitesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StartAnswerRequestCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        List<Request> requests = RequestsService.getInstance().getUnansweredRequests();
        SuitesService suitesService = SuitesService.getInstance();
        Function<Integer, List<Suite>> getPossibleSuitesForRequest = suitesService::getPossibleSuitesForRequest;
        List<Integer> amountsOfSuites = requests.stream()
                .map(Request::getId)
                .map(getPossibleSuitesForRequest)
                .map(List::size)
                .collect(Collectors.toList());
        request.setAttribute(Constants.ATTRIBUTE_UNANSWERED_REQUESTS, requests);
        request.setAttribute(Constants.ATTRIBUTE_AMOUNT_OF_SUITES_FOR_UNANSWERED_REQUESTS, amountsOfSuites);
        return new ForwardPage(Constants.UNANSWERED_REQUESTS_PAGE_URL);
    }
}
