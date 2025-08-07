//package com.demo.model;
//
//
//import jakarta.persistence.*;
//
//@Entity
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    private String commentText;
//
//    public Comment() {
//    }
//
//    public Comment(int id, Post post, User user, String commentText) {
//        this.id = id;
//        this.post = post;
//        this.user = user;
//        this.commentText = commentText;
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
//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
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
//    public String getCommentText() {
//        return commentText;
//    }
//
//    public void setCommentText(String commentText) {
//        this.commentText = commentText;
//    }
//}



package com.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String commentText;

    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(int id, Post post, User user, String commentText) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.commentText = commentText;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
