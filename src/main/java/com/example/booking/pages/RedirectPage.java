package com.example.booking.pages;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectPage extends AbstractPage {
    private static final Logger LOGGER = Logger.getLogger(RedirectPage.class);

    public RedirectPage(String url) {
        super(url);
    }

    @Override
    public void finishRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }

    @Override
    public String toString() {
        return "RedirectPage to " + url;
    }
}
