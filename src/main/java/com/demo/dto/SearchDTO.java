package com.demo.dto;

public class SearchDTO {
    private int id;
    private String profileImage;
    private String profileName;
    private String userName;
    private int followStatus;

    public SearchDTO(int id, String profileImage, String profileName, String userName, int followStatus) {
        this.id = id;
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.userName = userName;
        this.followStatus = followStatus;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }
}
