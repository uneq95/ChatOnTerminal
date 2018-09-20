package com.chat.domain;

public abstract class GenericMessage {

    private MsgType type;

    public GenericMessage(MsgType type) {
        this.type = type;
    }


    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public enum MsgType {
        LOGIN_MSG, CHAT_MSG
    }
}
