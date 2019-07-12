package com.deere.aglogic.messaging.core;

public class MessageBodyImpl implements MessageBody {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
