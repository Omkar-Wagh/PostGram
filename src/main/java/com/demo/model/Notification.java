package com.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // The user who receives the notification
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // The user who triggered the notification (e.g., liked, followed, etc.)
    @ManyToOne
    @JoinColumn(name = "trigger_user_id")
    private User triggerUser;

    private int type;
    private LocalDateTime createdAt;
    private boolean isSeen;

    public Notification() {
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isSeen = false; // default to unseen when created
    }
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTriggerUser() {
        return triggerUser;
    }

    public void setTriggerUser(User triggerUser) {
        this.triggerUser = triggerUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
