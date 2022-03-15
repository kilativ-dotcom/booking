package com.example.booking.commands;

import com.example.booking.pages.AbstractPage;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    AbstractPage execute(HttpServletRequest request);
}
