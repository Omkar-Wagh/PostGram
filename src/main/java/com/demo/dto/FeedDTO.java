package com.demo.dto;

public class FeedDTO {
    private int id;
    private String imageName;
    private String imageType;
    private String base64Image;
    private int likeCount;
    private int commentCount;

    public FeedDTO() {}

    public FeedDTO(int id, String imageName, String imageType, String base64Image,
                   int likeCount, int commentCount) {
        this.id = id;
        this.imageName = imageName;
        this.imageType = imageType;
        this.base64Image = base64Image;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    // === Getters and Setters ===

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
