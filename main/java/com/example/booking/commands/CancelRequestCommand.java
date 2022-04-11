package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.service.RequestsService;

import javax.servlet.http.HttpServletRequest;

public class CancelRequestCommand implements Command{
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        int requestId = Integer.parseInt(String.valueOf(request.getParameter(Constants.PARAMETER_CANCELLED_REQUEST_ID)));
// TODO: 07.03.2022 make filter to allow only integers here
        RequestsService.getInstance().cancelRequestById(requestId);

        return new ViewUserRequestsCommand().execute(request);
    }
}
