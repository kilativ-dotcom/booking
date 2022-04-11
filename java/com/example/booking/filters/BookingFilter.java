package com.example.booking.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//@WebFilter(filterName = "BookingFilter")
/*
* booking/ - for user - show booking form, for admin - start answer
* booking/book - for user, send filled form
* booking/answer
* booking/
*
* */
public class BookingFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
    }
}
