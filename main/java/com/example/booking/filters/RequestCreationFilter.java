package com.example.booking.filters;

import com.example.booking.constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@WebFilter(filterName = "RequestCreationFilter", servletNames = {"FrontControllerServlet"})
public class RequestCreationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(RequestCreationFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.trace("in RequestCreationFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String command = httpRequest.getParameter(Constants.PARAM_COMMAND);
        if (command == null || !command.equalsIgnoreCase("booking")) {
            LOGGER.trace("command does not need request form validation");
            chain.doFilter(request, response);
            return;
        }
        LOGGER.trace("command needs request form validation");

        String checkInDate = httpRequest.getParameter(Constants.PARAMETER_CHECK_IN_DATE);
        String checkOutDate = httpRequest.getParameter(Constants.PARAMETER_CHECK_OUT_DATE);
        String comment = httpRequest.getParameter(Constants.PARAMETER_COMMENT);
        String guests = httpRequest.getParameter(Constants.PARAMETER_GUESTS);
        String suiteClassId = httpRequest.getParameter(Constants.PARAMETER_SUITE_CLASS + "Id");
        String suiteClass = httpRequest.getParameter(Constants.PARAMETER_SUITE_CLASS);

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("in    = " + checkInDate);
            LOGGER.trace("out   = " + checkOutDate);
            LOGGER.trace("comm  = " + comment);
            LOGGER.trace("guests= " + guests);
            LOGGER.trace("scid  = " + suiteClassId);
            LOGGER.trace("sc    = " + suiteClass);
        }

        if(abortIsNeeded(checkInDate, checkOutDate, comment, guests, suiteClassId, suiteClass)){
            abort(httpRequest, httpResponse, checkInDate, checkOutDate, comment, guests, suiteClassId, suiteClass, command);
            return;
        }

        chain.doFilter(request, response);
    }

    private static boolean abortIsNeeded(String checkInDate, String checkOutDate, String comment, String guests, String suiteClassId, String suiteClass){
        if (checkInDate == null || checkInDate.trim().isEmpty()
                || checkOutDate == null || checkOutDate.trim().isEmpty()
                || comment == null
                || guests == null || guests.trim().isEmpty()
                || suiteClassId == null || suiteClassId.trim().isEmpty()
                || suiteClass == null || suiteClass.trim().isEmpty()) {
            String message = "one or more element was not sent";
            LOGGER.trace(message);
            return true;
        }
        if (!isCorrectDate(checkInDate) || !isCorrectDate(checkOutDate) || !isSecondDateAfterFirst(checkInDate, checkOutDate)) {
            String message = "date(s) is(are) in wrong format";
            LOGGER.trace(message);
            return true;
        }
        if (!isNaturalNumber(guests)) {
            String message = "amount of guests should be natural number";
            LOGGER.trace(message);
            return true;
        }
        if (!isNaturalNumber(suiteClassId)) {
            String message = "for some reason suiteClassId is not natural number but it should be natural number";
            LOGGER.trace(message);
            return true;
        }
        return false;
    }

    private static boolean isCorrectDate(String possibleDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(possibleDate);
            return true;
        } catch (ParseException ignored) {
            return false;
        }
    }

    private static boolean isSecondDateAfterFirst(String first, String second) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date firstDate = dateFormat.parse(first);
            Date secondDate = dateFormat.parse(second);
            return secondDate.after(firstDate);
        } catch (ParseException ignored) {
            return false;
        }
    }

    private static boolean isNaturalNumber(String possibleNumber) {
        try {
            int number = Integer.parseInt(possibleNumber);
            return number > 0;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    private void abort(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String checkInDate, String checkOutDate, String comment, String guests, String suiteClassId, String suiteClass, String command) throws ServletException, IOException {
        bindAttributes(httpRequest, checkInDate, checkOutDate, comment, guests, suiteClassId, suiteClass);
        sendBack(httpRequest, httpResponse, command);
    }


    private static void bindAttributes(HttpServletRequest request, String checkInDate, String checkOutDate, String comment, String guests, String suiteClassId, String suiteClass) {
        if (checkInDate != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_CHECK_IN_DATE, checkInDate);
        }
        if (checkOutDate != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_CHECK_OUT_DATE, checkOutDate);
        }
        if (comment != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_COMMENT, comment);
        }
        if (guests != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_GUESTS_AMOUNT, guests);
        }
        if (suiteClassId != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_SUITE_CLASS_ID, suiteClassId);
        }
        if (suiteClass != null) {
            request.setAttribute(Constants.ATTRIBUTE_BOOKING_PREV_SUITE_CLASS_NAME, suiteClass);
        }
    }

    private static void sendBack(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String command) throws ServletException, IOException {
        String url = Constants.FRONT_CONTROLLER_SERVLET_VALUE + "?" + Constants.PARAM_COMMAND + "=prepare" + command;
        LOGGER.info("forwarding to " + url);
        RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher(url);
        requestDispatcher.forward(httpRequest, httpResponse);
    }
}
