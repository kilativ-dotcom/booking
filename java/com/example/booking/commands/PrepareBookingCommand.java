package com.example.booking.commands;

import com.example.booking.constants.Constants;
import com.example.booking.entity.SuiteClass;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.service.SuitesService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PrepareBookingCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(PrepareBookingCommand.class);

    @Override
    public AbstractPage execute(HttpServletRequest request) {
        List<SuiteClass> suiteClasses = SuitesService.getInstance().getAllSuiteClasses();
        LOGGER.trace("Found " + suiteClasses.size() + " classes");
        request.setAttribute(Constants.ATTRIBUTE_SUITE_CLASSES, suiteClasses);
        return new ForwardPage(Constants.BOOK_SUITE_PAGE_URL);
    }
}
