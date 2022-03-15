package com.example.booking.entity;

public class Response {
    private       int id;
    private final String responseTime;
    private final Request request;
    private final Suite suite;
    private final User creator;

    public Response(int id, String responseTime, Request request, Suite suite, User creator) {
        this.id = id;
        this.responseTime = responseTime;
        this.request = request;
        this.suite = suite;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public Request getRequest() {
        return request;
    }

    public Suite getSuite() {
        return suite;
    }

    public User getCreator() {
        return creator;
    }
}
