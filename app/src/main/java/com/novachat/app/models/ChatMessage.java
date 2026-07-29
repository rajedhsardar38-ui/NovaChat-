package com.novachat.app.models;

public class ChatMessage {
    private String messageId;
    private String senderId;
    private String senderName;
    private String text;
    private String time;
    private boolean isSentByMe;
    private String type; // TEXT, IMAGE, VOICE
    private boolean isRead;

    public ChatMessage() {}

    public ChatMessage(String messageId, String senderId, String senderName, String text, String time, boolean isSentByMe, String type, boolean isRead) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.text = text;
        this.time = time;
        this.isSentByMe = isSentByMe;
        this.type = type;
        this.isRead = isRead;
    }

    public String getMessageId() { return messageId; }
    public String getSenderId() { return senderId; }
    public String getSenderName() { return senderName; }
    public String getText() { return text; }
    public String getTime() { return time; }
    public boolean isSentByMe() { return isSentByMe; }
    public String getType() { return type; }
    public boolean isRead() { return isRead; }

    public void setSentByMe(boolean sentByMe) { isSentByMe = sentByMe; }
    public void setText(String text) { this.text = text; }
}
