package com.demo.dto;

public class CommentDTO {

    private int id;
    private int userId;
    private String profileImage;
    private String profileName;
    private String userName;
    private String timeAgo;
    private String commentText;
    private int commentStatus;

    public CommentDTO(int id,int userId, String profileImage, String profileName, String userName, String timeAgo, String commentText, int commentStatus) {
        this.id = id;
        this.userId = userId;
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.userName = userName;
        this.timeAgo = timeAgo;
        this.commentText = commentText;
        this.commentStatus = commentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getComment() {
        return commentText;
    }

    public void setComment(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }
}
