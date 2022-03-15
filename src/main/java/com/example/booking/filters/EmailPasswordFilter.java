package com.example.booking.filters;

import com.example.booking.constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "EmailPasswordFilter", servletNames = {"FrontControllerServlet"})
public class EmailPasswordFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(EmailPasswordFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.trace("in EmailPasswordFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String command = httpRequest.getParameter(Constants.PARAM_COMMAND);
        if (command==null || (!command.equalsIgnoreCase("login") && !command.equalsIgnoreCase("signup"))){
            LOGGER.trace("Command does not need name and password");
            chain.doFilter(request, response);
            return;
        }
        LOGGER.trace("Command requires name and password");
        String name = httpRequest.getParameter(Constants.PARAMETER_NAME);
        String password = httpRequest.getParameter(Constants.PARAMETER_PASSWORD);
        if (name != null && password != null) {
            LOGGER.trace("Name and password are not null");
            if (!name.trim().isEmpty() && !password.trim().isEmpty()) {
                LOGGER.trace("Name and password are not empty");
                chain.doFilter(request, response);
            } else{
                LOGGER.info("Attempt to log in or sign up with empty name or password");
                sendBack(httpRequest, httpResponse, command);
            }
        } else {
            LOGGER.trace("Name or password is null");
            sendBack(httpRequest, httpResponse, command);
        }
    }

    private static void sendBack(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String command) throws ServletException, IOException {
        String message = "login or password is null or empty";
        if (command.equalsIgnoreCase("login")){
            httpRequest.setAttribute(Constants.LOGIN_PAGE_MESSAGE, message);
        } else if (command.equalsIgnoreCase("signup")){
            httpRequest.setAttribute(Constants.SIGNUP_PAGE_MESSAGE, message);
        }
        String url = Constants.FRONT_CONTROLLER_SERVLET_VALUE + "?" + Constants.PARAM_COMMAND + "=prepare" + command;
        LOGGER.info("forwarding to " + url);
        RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher(url);
        requestDispatcher.forward(httpRequest, httpResponse);
    }
}