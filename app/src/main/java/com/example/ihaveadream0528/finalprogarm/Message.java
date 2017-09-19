package com.example.ihaveadream0528.finalprogarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String messageText;
    private String messageName;
    private String messageTime;

    public Message(String messageText, String messageName) {
        this.messageText = messageText;
        this.messageName = messageName;

        // Initialize to current time
        DateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm:ss");
        messageTime = dateFormat.format(new Date().getTime());
    }

    public Message(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageName;
    }

    public void setMessageUser(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
