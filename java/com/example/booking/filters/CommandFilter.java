package com.example.booking.filters;

import com.example.booking.commands.Command;
import com.example.booking.commands.CommandFactory;
import com.example.booking.constants.Constants;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@WebFilter(filterName = "CommandFilter"/*, servletNames = {"FrontControllerServlet"}*/, urlPatterns = {"/" + Constants.FRONT_CONTROLLER_SERVLET_VALUE})
public class CommandFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommandFilter.class);
    private static final Set<String> adminCommands = new HashSet<>(Arrays.asList(
            "LOGOUT",
            "PREPAREANSWERREQUEST",
            "SELECTSUITEFORREQUEST",
            "STARTANSWERREQUEST",
            "VIEWREQUESTS",
            "VIEWRESPONSES",
            "VIEWSUITECLASSES",
            "VIEWSUITES",
            "VIEWUSERS",
            "CREATEUSERS"
    ));

    private static final Set<String> notAdminCommands = new HashSet<>(Arrays.asList(
            "BOOKING",
            "CANCELREQUEST",
            "LOGOUT",
            "PREPAREBOOKING",
            "VIEWUSERREQUESTS",
            "VIEWUSERRESPONSES"
    ));

    private static final Set<String> guestCommands = new HashSet<>(Arrays.asList(
            "LOGIN",
            "PREPARELOGIN",
            "PREPARESIGNUP",
            "SIGNUP"
    ));

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        String command = httpRequest.getParameter(Constants.PARAM_COMMAND).toUpperCase(Locale.ROOT);
        LOGGER.trace(String.format("User %s is executing command %s", user, command));

        if (null == user) {
            LOGGER.trace("User is not logged");
            if (guestCommands.contains(command)) {
                LOGGER.trace("Authorized access");
                addCommandFromFactoryToRequest(httpRequest, command);
                chain.doFilter(request, response);
            } else {
                LOGGER.trace("Attempt of unauthorized access. Sending to login page");
                AbstractPage page = CommandFactory.getCommand("PREPARELOGIN").execute(httpRequest);
                page.finishRequest(httpRequest, httpResponse);
            }
            return;
        }

        boolean notAdminExecutingNotAdminCommand = !user.isAdmin() && notAdminCommands.contains(command);
        boolean adminExecutingAdminCommand = user.isAdmin() && adminCommands.contains(command);
        if (notAdminExecutingNotAdminCommand || adminExecutingAdminCommand) {
            LOGGER.trace("Authorized access");
            addCommandFromFactoryToRequest(httpRequest, command);
        } else {
            LOGGER.trace("Attempt of unauthorized access. Sending to welcome page");
            AbstractPage page = new ForwardPage("/welcome");
            page.finishRequest(httpRequest, httpResponse);
            return;
        }
        chain.doFilter(request, response);
    }

    private void addCommandFromFactoryToRequest(HttpServletRequest httpRequest, String command) {
        httpRequest.setAttribute(Constants.ATTRIBUTE_COMMAND, CommandFactory.getCommand(command));
    }
}
