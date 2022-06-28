package com.example.booking.filters;

import com.example.booking.commands.CommandFactory;
import com.example.booking.constants.Constants;
import com.example.booking.entity.User;
import com.example.booking.pages.AbstractPage;
import com.example.booking.pages.ForwardPage;
import com.example.booking.pages.RedirectPage;
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

//@WebFilter(filterName = "ViewFilter")
public class ViewFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(ViewFilter.class);
    private static final String ARMIN_PREFIX = "view";
    private static final Set<String> adminCanView = new HashSet<>(Arrays.asList(
            "REQUESTS",
            "RESPONSES",
            "SUITECLASSES",
            "SUITES",
            "USERS"
    ));

    private static final String NOT_ADMIN_PREFIX = "viewUser";
    private static final Set<String> notAdminCanView = new HashSet<>(Arrays.asList(
            "REQUESTS",
            "RESPONSES"
    ));


    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.trace("in ViewFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute(Constants.ATTRIBUTE_USER);
        if (null == user){
            LOGGER.trace("Unauthenticated user tries to view information");
            AbstractPage page = CommandFactory.getCommand("prepareLogin").execute(httpRequest);
            page.finishRequest(httpRequest, httpResponse);
            return;
        }

        String target = httpRequest.getPathInfo();
        if (null != target){
            target = target.replace("/", "").toUpperCase(Locale.ROOT);
        }
        LOGGER.trace("pathInfo = " + target);
        LOGGER.trace("user = " + user);
        if (user.isAdmin() && adminCanView.contains(target)){
            httpRequest.setAttribute(Constants.ATTRIBUTE_COMMAND, CommandFactory.getCommand(ARMIN_PREFIX + target));
        } else if (!user.isAdmin() && notAdminCanView.contains(target)){
            httpRequest.setAttribute(Constants.ATTRIBUTE_COMMAND, CommandFactory.getCommand(NOT_ADMIN_PREFIX + target));
        } else{
            LOGGER.trace("Attempt of unauthorized access. Sending to welcome page");
            AbstractPage page = new RedirectPage("welcome");
            page.finishRequest(httpRequest, httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }
}
