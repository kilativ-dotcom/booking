package com.example.booking.entity;

import java.util.Objects;

public class User {
    private final int id;
    private final String email;
    private final boolean isAdmin;

    public User(int id, String email, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, isAdmin);
    }

    @Override
    public String toString() {
        return email + (isAdmin? ";admin" : ";notAdmin");
    }
}
