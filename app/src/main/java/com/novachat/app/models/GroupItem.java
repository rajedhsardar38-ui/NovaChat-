package com.novachat.app.models;

public class GroupItem {
    private String groupId;
    private String name;
    private int memberCount;
    private String lastMessage;
    private String time;
    private int unreadCount;

    public GroupItem(String groupId, String name, int memberCount, String lastMessage, String time, int unreadCount) {
        this.groupId = groupId;
        this.name = name;
        this.memberCount = memberCount;
        this.lastMessage = lastMessage;
        this.time = time;
        this.unreadCount = unreadCount;
    }

    public String getGroupId() { return groupId; }
    public String getName() { return name; }
    public int getMemberCount() { return memberCount; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public int getUnreadCount() { return unreadCount; }
}
