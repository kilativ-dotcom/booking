package com.example.booking.filters;

import com.example.booking.constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(filterName = "LocaleChangeFilter", urlPatterns = {"/*"})
public class LocaleChangeFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LocaleChangeFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String newLocale = httpServletRequest.getParameter(Constants.PARAMETER_CHANGE_LOCALE);

        if (newLocale!=null){
            LOGGER.trace("Change session locale to " + newLocale);
            httpServletRequest.getSession().setAttribute(Constants.ATTRIBUTE_SESSION_LOCALE, newLocale);
        }

        chain.doFilter(request, response);
    }
}
