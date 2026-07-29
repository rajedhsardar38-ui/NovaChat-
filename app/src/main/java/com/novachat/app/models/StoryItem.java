package com.novachat.app.models;

public class StoryItem {
    private String id;
    private String name;
    private String avatarUrl;
    private boolean isUnread;

    public StoryItem(String id, String name, String avatarUrl, boolean isUnread) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.isUnread = isUnread;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isUnread() { return isUnread; }
}
