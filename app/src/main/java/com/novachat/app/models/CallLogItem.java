package com.novachat.app.models;

public class CallLogItem {
    private String id;
    private String name;
    private String avatarUrl;
    private String callType; // VOICE, VIDEO
    private String direction; // INCOMING, OUTGOING, MISSED
    private String time;

    public CallLogItem(String id, String name, String avatarUrl, String callType, String direction, String time) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.callType = callType;
        this.direction = direction;
        this.time = time;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getCallType() { return callType; }
    public String getDirection() { return direction; }
    public String getTime() { return time; }
}
