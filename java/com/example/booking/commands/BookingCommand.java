package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Request;
import com.example.booking.entity.SuiteClass;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.RedirectPage;
import com.example.booking.service.RequestsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class BookingCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        String checkInDate = request.getParameter(Constants.PARAMETER_CHECK_IN_DATE);
        String checkOutDate = request.getParameter(Constants.PARAMETER_CHECK_OUT_DATE);
        int guests = Integer.parseInt(request.getParameter(Constants.PARAMETER_GUESTS));
        String comment = request.getParameter(Constants.PARAMETER_COMMENT);
        String suiteClass = request.getParameter(Constants.PARAMETER_SUITE_CLASS);
        int suiteClassId = Integer.parseInt(request.getParameter(Constants.PARAMETER_SUITE_CLASS + "Id"));

        Optional<Request> optionalRequest = RequestsService.getInstance().creteRequest(user, checkInDate, checkOutDate, guests, comment, new SuiteClass(suiteClassId, suiteClass));
        if (optionalRequest.isPresent()){
            request.setAttribute(Constants.ATTRIBUTE_CREATED_REQUEST, optionalRequest.get());
        }
        return new RedirectPage("welcome");
    }
}
