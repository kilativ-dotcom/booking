package com.example.booking.entity;

public class SuiteClass {
    private final int id;
    private final String name;

    public SuiteClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
