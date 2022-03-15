package com.example.booking.controller;

import com.example.booking.commands.Command;
import com.example.booking.commands.CommandFactory;
import com.example.booking.constants.Constants;
import com.example.booking.pages.AbstractPage;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", value = "/" + Constants.FRONT_CONTROLLER_SERVLET_VALUE)
public class FrontControllerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FrontControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("get request:" + request.getRequestURL().toString());
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.trace("post request:" + request.getRequestURL().toString());
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
        String commandParam = request.getParameter(Constants.PARAM_COMMAND);
        Command command = CommandFactory.getCommand(commandParam);
        LOGGER.trace("Command from factory: " + command);
        AbstractPage page = command.execute(request);
        LOGGER.trace("Page after command.execute: " + page);
        page.finishRequest(request, response);
    }
}
