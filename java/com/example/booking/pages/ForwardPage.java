package com.example.booking.pages;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardPage extends AbstractPage {
    private static final Logger LOGGER = Logger.getLogger(ForwardPage.class);

    public ForwardPage(String url) {
        super(url);
    }

    @Override
    public void finishRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("Forwarding to " + url);
        ServletContext context = request.getServletContext().getContext(request.getServletContext().getContextPath());
        context.getRequestDispatcher("/" + url).forward(request, response);
    }

    @Override
    public String toString() {
        return "ForwardPage to " + url;
    }
}
