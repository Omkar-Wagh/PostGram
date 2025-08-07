package com.demo.dto;

public class NotificationDTO {

    private int id;
    private String profileImage;
    private String profileName;
    private int type;
    private String timeAgo;

    public NotificationDTO(int id, String profileImage, String profileName, int type, String timeAgo) {
        this.id = id;
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.type = type;
        this.timeAgo = timeAgo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}
