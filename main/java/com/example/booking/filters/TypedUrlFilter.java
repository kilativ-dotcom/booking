package com.example.booking.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "TypedUrlFilter", urlPatterns = {"/" + Constants.FRONT_CONTROLLER_SERVLET_VALUE})
public class TypedUrlFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(TypedUrlFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        LOGGER.trace("TypedUrlFilter for " + httpRequest.getRequestURL());
        String referer = httpRequest.getHeader("referer");
        if (referer == null) {
            LOGGER.trace("URL was typed into URLField");
            httpResponse.sendRedirect(httpRequest.getContextPath());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
