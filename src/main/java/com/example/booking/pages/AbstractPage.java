package com.example.booking.pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractPage {
    protected final String url;

    public AbstractPage(String url) {
        this.url = url;
    }

    public abstract void finishRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
