package com.example.booking.controller;

import com.example.booking.connection.ConnectionPoolImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String log4jConfigPath = context.getRealPath("") + File.separator + context.getInitParameter("log4j-config-location");
        PropertyConfigurator.configure(log4jConfigPath);
        Logger logger = Logger.getLogger(ContextListener.class);
        logger.info("Application started");
        String propertiesLocation = context.getRealPath("") + File.separator + context.getInitParameter("connection-pool-config-location");
        if (!ConnectionPoolImpl.configure(propertiesLocation) || ConnectionPoolImpl.getInstance() == null) {
            logger.fatal("Cannot properly initialize connection pool.");
            throw new IllegalArgumentException();
        }
        sce.getServletContext().setRequestCharacterEncoding("UTF-8");
        sce.getServletContext().setResponseCharacterEncoding("UTF-8");
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}
