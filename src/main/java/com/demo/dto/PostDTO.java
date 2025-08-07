package com.demo.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private int id;
    private String caption;
    private String imageName;
    private String imageType;
    private String base64Image;
    private LocalDateTime createdAt;
    private String timeAgo;
    private String userName;
    private String username;
    private String userProfileImage;

    private int likeCount;
    private int commentCount;
    private int userId;
    private int likedByCurrentUser;
    private int postStatus;

    public PostDTO() {}

    public PostDTO(int id, String caption, String imageName, String imageType, String base64Image,
                   LocalDateTime createdAt,String timeAgo,String userName, String username, String userProfileImage,
                   int likeCount, int commentCount,int userId,int likedByCurrentUser,int postStatus) {
        this.id = id;
        this.caption = caption;
        this.imageName = imageName;
        this.imageType = imageType;
        this.base64Image = base64Image;
        this.createdAt = createdAt;
        this.timeAgo = timeAgo;
        this.userName = userName;
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.userId = userId;
        this.likedByCurrentUser = likedByCurrentUser;
        this.postStatus = postStatus;
    }

    // === Getters and Setters ===

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCaption() { return caption; }

    public void setCaption(String caption) { this.caption = caption; }

    public String getImageName() { return imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getImageType() { return imageType; }

    public void setImageType(String imageType) { this.imageType = imageType; }

    public String getBase64Image() { return base64Image; }

    public void setBase64Image(String base64Image) { this.base64Image = base64Image; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getTimeAgo() { return timeAgo; }
    public void setTimeAgo(String timeAgo) { this.timeAgo = timeAgo; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getUserProfileImage() { return userProfileImage; }

    public void setUserProfileImage(String userProfileImage) { this.userProfileImage = userProfileImage; }

    public int getLikeCount() { return likeCount; }

    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public int getCommentCount() { return commentCount; }

    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(int likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }
}
