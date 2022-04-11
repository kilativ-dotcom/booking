package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.Response;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.ResponsesService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewResponsesCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(ViewResponsesCommand.class);
    @Override
    public AbstractPage execute(HttpServletRequest request) {
        List<Response> all = ResponsesService.getInstance().getAll();
        LOGGER.trace(String.format("Found %s responses%n", all.size()));
        request.setAttribute(Constants.ATTRIBUTE_ALL_RESPONSES, all);
        return new ForwardPage(Constants.RESPONSES_FOR_ADMIN_PAGE_URL);
    }
}
