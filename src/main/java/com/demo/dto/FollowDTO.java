package com.demo.dto;

public class FollowDTO {
    private int id;
    private String profileImage;
    private String userName;
    private String profileName;
    int followStatus;

    public FollowDTO() {
    }

    public FollowDTO(int id, String profileImage, String userName, String profileName,int followStatus) {
        this.id = id;
        this.profileImage = profileImage;
        this.userName = userName;
        this.profileName = profileName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }
}
