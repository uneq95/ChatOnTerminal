package com.chat.domain;

import java.util.Objects;

public class User {

    private String username;
    private String feature;
    private String criterion;


    public User(String username, String feature, String criterion) {
        this.username = username;
        this.feature = feature;
        this.criterion = criterion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", feature='" + feature + '\'' +
                ", criterion='" + criterion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username);
    }
}
