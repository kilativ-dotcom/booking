package com.example.booking.entity;

import java.util.Objects;

public class Request {
    private       int           id;
    private final String        requestTime;
    private final String        checkInDate;
    private final String        checkOutDate;
    private final int           guests;
    private final SuiteClass    suiteClass;
    private final String        comment;
    private final boolean       isCancelled;
    private final User          creator;

    public Request(int id, String requestTime, String checkInDate, String checkOutDate, int guests, SuiteClass suiteClass, String comment, boolean isCancelled, User creator) {
        this.id             = id;
        this.requestTime    = requestTime;
        this.checkInDate    = checkInDate;
        this.checkOutDate   = checkOutDate;
        this.guests         = guests;
        this.suiteClass     = suiteClass;
        this.comment        = comment;
        this.isCancelled    = isCancelled;
        this.creator        = creator;
    }

    public int getId() {
        return id;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public int getGuests() {
        return guests;
    }

    public SuiteClass getSuiteClass() {
        return suiteClass;
    }

    public String getComment() {
        return comment;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public User getCreator() {
        return creator;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id && guests == request.guests && isCancelled == request.isCancelled && creator == request.creator && requestTime.equals(request.requestTime) && checkInDate.equals(request.checkInDate) && checkOutDate.equals(request.checkOutDate) && suiteClass.equals(request.suiteClass) && comment.equals(request.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestTime, checkInDate, checkOutDate, guests, suiteClass, comment, isCancelled, creator);
    }
}
