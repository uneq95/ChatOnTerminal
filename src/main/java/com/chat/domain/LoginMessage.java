package com.chat.domain;

import java.util.Objects;

public class LoginMessage extends GenericMessage {

    private User loggedInUser;

    public LoginMessage() {
        super(MsgType.LOGIN_MSG);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginMessage that = (LoginMessage) o;
        return Objects.equals(loggedInUser, that.loggedInUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(loggedInUser);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "loggedInUser=" + loggedInUser +
                '}';
    }
}
