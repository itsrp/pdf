package com.deere.aglogic.messaging.email;

import com.deere.aglogic.messaging.core.Message;
import com.deere.aglogic.messaging.core.MessageSender;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class EmailMessageSender implements MessageSender<EmailMessage> {

    /*@Override
    public void sendMessage(Message message) {
        Email email = buildEmail(message);
        try {
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }*/

    private Email buildEmail(Message message) {
        return null;
    }

    @Override
    public void sendMessage(EmailMessage emailMessage) {

    }
}
