package com.example.booking.entity;

public class Suite {
    private final int id;
    private final String roomNumber;
    private final int maxGuests;
    private final SuiteClass suiteClass;
    private final int costPerNight;

    public Suite(int id, String roomNumber, int maxGuests, SuiteClass suiteClass, int costPerNight) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxGuests = maxGuests;
        this.suiteClass = suiteClass;
        this.costPerNight = costPerNight;
    }

    public int getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public SuiteClass getSuiteClass() {
        return suiteClass;
    }

    public int getCostPerNight() {
        return costPerNight;
    }
}
