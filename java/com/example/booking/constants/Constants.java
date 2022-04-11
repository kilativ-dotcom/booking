package com.example.booking.constants;

import java.io.File;

public class Constants {
    public static final String PARAMETER_WRONG_COMMAND = "wrong command";
    public static final String PARAMETER_NAME = "login";
    public static final String PARAMETER_PASSWORD = "password";

    public static final String PARAMETER_CHANGE_LOCALE = "changeLocale";

    public static final String PARAMETER_CHECK_IN_DATE = "checkInDate";
    public static final String PARAMETER_CHECK_OUT_DATE = "checkOutDate";
    public static final String PARAMETER_GUESTS = "amountOfGuests";
    public static final String PARAMETER_COMMENT = "comment";
    public static final String PARAMETER_SUITE_CLASS = "suiteClass";

    public static final String PARAMETER_CANCELLED_REQUEST_ID = "cancelledRequestId";

    public static final String PARAMETER_PREPARE_ANSWER_REQUEST = "request id";
    public static final String PARAMETER_REQUEST_FOR_SUITE = "request id for suite";
    public static final String PARAMETER_SUITE_FOR_REQUEST = "suite for request";

    public static final String PARAMETER_USER_TO_DELETE = "delete user";
    public static final String PARAMETER_USER_TO_CHANGE = "change user";

    public static final String PARAMETER_SUITE_TO_DELETE = "delete suite";
    public static final String PARAMETER_SUITE_TO_CHANGE = "change suite";

    public static final String PARAMETER_SUITE_CLASS_TO_DELETE = "delete suite class";
    public static final String PARAMETER_SUITE_CLASS_TO_CHANGE = "change suite class";

    public static final String PARAMETER_REQUEST_TO_DELETE = "delete request";
    public static final String PARAMETER_REQUEST_TO_CHANGE = "change request";

    public static final String PARAMETER_RESPONSE_TO_DELETE = "delete response";
    public static final String PARAMETER_RESPONSE_TO_CHANGE = "change response";

    public static final String PARAMETER_NEW_USERS_TABLE_EMAIL = "new email";
    public static final String PARAMETER_NEW_USERS_TABLE_PASSWORD = "new password";
    public static final String PARAMETER_NEW_USERS_TABLE_IS_ADMIN = "new is admin";

    public static final String AUTHENTICATION_EMPTY_PARAMETER = "empty authorization parameter";
    public static final String LOGIN_WRONG_LOGIN_OR_PASSWORD = "wrong login or password";
    public static final String SIGNUP_MESSAGE_BAD_CREDENTIALS = "signup message";

    public static final String INDEX_PAGE_URL = "index.jsp";
    private static final String JSP_FOLDER = "WEB-INF" + File.separator + "jsp";
    public static final String LOGIN_PAGE_URL =                     JSP_FOLDER + File.separator + "login.jsp";
    public static final String SIGNUP_PAGE_URL =                    JSP_FOLDER + File.separator  + "signup.jsp";
    public static final String WELCOME_PAGE_URL =                   JSP_FOLDER + File.separator  + "welcome.jsp";
    public static final String WELCOME_ADMIN_PAGE_URL =             JSP_FOLDER + File.separator  + "welcomeAdmin.jsp";
    public static final String WELCOME_USER_PAGE_URL =              JSP_FOLDER + File.separator  + "welcomeUser.jsp";
    public static final String BOOK_SUITE_PAGE_URL =                JSP_FOLDER + File.separator  + "bookSuite.jsp";
    public static final String REQUESTS_FOR_USER_PAGE_URL =         JSP_FOLDER + File.separator  + "requestsUser.jsp";
    public static final String RESPONSES_FOR_USER_PAGE_URL =        JSP_FOLDER + File.separator  + "responsesUser.jsp";
    public static final String REQUESTS_FOR_ADMIN_PAGE_URL =        JSP_FOLDER + File.separator  + "requestsAdmin.jsp";
    public static final String RESPONSES_FOR_ADMIN_PAGE_URL =       JSP_FOLDER + File.separator  + "responsesAdmin.jsp";
    public static final String USERS_FOR_ADMIN_PAGE_URL =           JSP_FOLDER + File.separator  + "usersAdmin.jsp";
    public static final String SUITES_FOR_ADMIN_PAGE_URL =          JSP_FOLDER + File.separator  + "suitesAdmin.jsp";
    public static final String SUITE_CLASSES_FOR_ADMIN_PAGE_URL =   JSP_FOLDER + File.separator  + "suiteClassesAdmin.jsp";
    public static final String UNANSWERED_REQUESTS_PAGE_URL =       JSP_FOLDER + File.separator  + "unansweredRequests.jsp";
    public static final String SUITABLE_SUITES_PAGE_URL =           JSP_FOLDER + File.separator  + "suitableSuites.jsp";
    public static final String LOCALE_HEADER_PAGE_URL =             JSP_FOLDER + File.separator  + "localeChanger.jsp";

    public static final String ATTRIBUTE_USER = "user";
    public static final String PARAM_COMMAND = "command";
    public static final String ATTRIBUTE_COMMAND = "command";

    public static final String PASSWORD_ENCRYPTION_ALGORITHM = "SHA-256";

    public static final String FRONT_CONTROLLER_SERVLET_VALUE = "frontControllerServlet";

    public static final String ATTRIBUTE_SESSION_LOCALE = "session locale";

    public static final String ATTRIBUTE_PREV_NAME = "prev name";
    public static final String ATTRIBUTE_SUITE_CLASSES = "suite classes";
    public static final String ATTRIBUTE_CREATED_REQUEST = "created request";

    public static final String ATTRIBUTE_BOOKING_MESSAGE = "booking message";

    public static final String ATTRIBUTE_BOOKING_PREV_CHECK_IN_DATE = "prev check in";
    public static final String ATTRIBUTE_BOOKING_PREV_CHECK_OUT_DATE = "prev check out";
    public static final String ATTRIBUTE_BOOKING_PREV_GUESTS_AMOUNT = "prev guests";
    public static final String ATTRIBUTE_BOOKING_PREV_COMMENT = "prev comment";
    public static final String ATTRIBUTE_BOOKING_PREV_SUITE_CLASS_ID = "prev suite class id";
    public static final String ATTRIBUTE_BOOKING_PREV_SUITE_CLASS_NAME = "prev suite class name";

    public static final String ATTRIBUTE_RESPONSES_FOR_USER_REQUESTS = "responses for user requests";
    public static final String ATTRIBUTE_REQUESTS_FOR_USER = "requests for user";

    public static final String ATTRIBUTE_UNANSWERED_REQUESTS = "unanswered requests";
    public static final String ATTRIBUTE_AMOUNT_OF_SUITES_FOR_UNANSWERED_REQUESTS = "amount of suites for unanswered requests";

    public static final String ATTRIBUTE_ALL_REQUESTS = "all requests";
    public static final String ATTRIBUTE_ALL_RESPONSES = "all responses";
    public static final String ATTRIBUTE_ALL_USERS = "all users";
    public static final String ATTRIBUTE_ALL_SUITES = "all suites";
    public static final String ATTRIBUTE_ALL_SUITE_CLASSES = "all suite classes";

    public static final String ATTRIBUTE_SUITABLE_SUITES = "suitable suites";
    public static final String ATTRIBUTE_REQUEST_ID = "request id";
    public static final String ATTRIBUTE_CREATED_RESPONSE = "created response";

}