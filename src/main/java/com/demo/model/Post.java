//package com.demo.model;
//
//import jakarta.persistence.*;
//
//
//@Entity
//public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Lob
//    private String caption;
//
//    private String imageName;
//    private String imageType;
//
//    @Lob
//    private byte[] imageData;
//
//    public Post() {
//    }
//
//    public Post(int id, User user, String caption, String imageName, String imageType, byte[] imageData) {
//        this.id = id;
//        this.user = user;
//        this.caption = caption;
//        this.imageName = imageName;
//        this.imageType = imageType;
//        this.imageData = imageData;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getCaption() {
//        return caption;
//    }
//
//    public void setCaption(String caption) {
//        this.caption = caption;
//    }
//
//    public String getImageName() {
//        return imageName;
//    }
//
//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }
//
//    public String getImageType() {
//        return imageType;
//    }
//
//    public void setImageType(String imageType) {
//        this.imageType = imageType;
//    }
//
//    public byte[] getImageData() {
//        return imageData;
//    }
//
//    public void setImageData(byte[] imageData) {
//        this.imageData = imageData;
//    }
//    @Transient
//    private String base64Image; // Not stored in DB, used for UI display
//
//    public String getBase64Image() {
//        return base64Image;
//    }
//
//    public void setBase64Image(String base64Image) {
//        this.base64Image = base64Image;
//    }
//}



package com.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String caption;

    private String imageName;
    private String imageType;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Transient
    private String base64Image; // Not stored in DB, used for UI display

    // ✅ Automatically set when post is created
    private LocalDateTime createdAt;

    public Post() {}

    public Post(int id, User user, String caption, String imageName, String imageType, byte[] imageData) {
        this.id = id;
        this.user = user;
        this.caption = caption;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // === Getters and Setters ===

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getCaption() { return caption; }

    public void setCaption(String caption) { this.caption = caption; }

    public String getImageName() { return imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getImageType() { return imageType; }

    public void setImageType(String imageType) { this.imageType = imageType; }

    public byte[] getImageData() { return imageData; }

    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public String getBase64Image() { return base64Image; }

    public void setBase64Image(String base64Image) { this.base64Image = base64Image; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
