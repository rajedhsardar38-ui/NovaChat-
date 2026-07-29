package com.novachat.app.models;

public class Contact {
    private String id;
    private String name;
    private String phone;
    private String avatarUrl;
    private String status;
    private boolean isOnline;

    public Contact(String id, String name, String phone, String avatarUrl, String status, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.isOnline = isOnline;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getStatus() { return status; }
    public boolean isOnline() { return isOnline; }
}
