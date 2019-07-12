package com.deere.aglogic.messaging.email;

import com.deere.aglogic.messaging.core.Message;
import com.deere.aglogic.messaging.core.MessageBody;
import com.deere.aglogic.messaging.core.MessageFooter;
import com.deere.aglogic.messaging.core.MessageHeader;

public class EmailMessage implements Message {

    private MessageHeader header;

    private MessageBody body;

    private MessageFooter footer;

    public EmailMessage(MessageHeader header, MessageBody body, MessageFooter footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public MessageHeader getHeader() {
        return header;
    }

    public MessageBody getBody() {
        return body;
    }

    public MessageFooter getFooter() {
        return footer;
    }
}
