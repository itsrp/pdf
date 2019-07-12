package com.deere.aglogic.messaging.core;

public interface MessageSender<T> {

    void sendMessage(T t);

}
