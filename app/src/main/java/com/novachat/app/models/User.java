package com.novachat.app.models;

public class User {
    private String uid;
    private String name;
    private String phone;
    private String email;
    private String avatarUrl;
    private String status;
    private boolean isOnline;

    public User() {}

    public User(String uid, String name, String phone, String email, String avatarUrl, String status, boolean isOnline) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.isOnline = isOnline;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { isOnline = online; }
}
