package com.example.booking.filters;

import com.example.booking.commands.CommandFactory;
import com.example.booking.commands.WrongCommand;
import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailPasswordFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(EmailPasswordFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.trace("in EmailPasswordFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        LOGGER.trace("Got request from " + httpRequest.getRequestURL().toString());
        String pathInfo = httpRequest.getPathInfo();
        LOGGER.trace("authentication path ends with " + pathInfo);
        if (null == pathInfo) {
            pathInfo = "";
        } else {
            pathInfo = pathInfo.replace("/", "");
        }
        if (pathInfo.isEmpty()) {
            pathInfo = "login";
        }
        if (!("login".equalsIgnoreCase(pathInfo) || "signup".equalsIgnoreCase(pathInfo))) {
            new WrongCommand().execute(httpRequest).finishRequest(httpRequest, httpResponse);
            return;
        }
        String name = httpRequest.getParameter(Constants.PARAMETER_NAME);
        String password = httpRequest.getParameter(Constants.PARAMETER_PASSWORD);
        if (name != null && password != null) {
            LOGGER.trace("Name and password are not null");
            if (!name.trim().isEmpty() && !password.trim().isEmpty()) {
                LOGGER.trace("Name and password are not empty");
                httpRequest.setAttribute(Constants.PARAM_COMMAND, CommandFactory.getCommand(pathInfo));
                chain.doFilter(request, response);
            } else {
                LOGGER.info("Attempt to log in or sign up with empty name or password");
                sendBack(httpRequest, httpResponse, pathInfo, true);
            }
        } else {
            LOGGER.trace("Name or password is null");
            sendBack(httpRequest, httpResponse, pathInfo, false);
        }
    }

    private static void sendBack(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String command, boolean addMessage) throws ServletException, IOException {
        httpRequest.setAttribute(Constants.AUTHENTICATION_EMPTY_PARAMETER, addMessage);
        String prepareCommand = "prepare" + command;
        httpRequest.setAttribute(Constants.ATTRIBUTE_COMMAND, CommandFactory.getCommand(prepareCommand));
        String url = Constants.FRONT_CONTROLLER_SERVLET_VALUE;// + "?" + Constants.PARAM_COMMAND + "=" + prepareCommand;
        LOGGER.info("forwarding to " + url);
        AbstractPage page = new ForwardPage(url);
        page.finishRequest(httpRequest, httpResponse);
    }
}