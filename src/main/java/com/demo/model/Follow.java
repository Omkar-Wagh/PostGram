package com.demo.model;

import jakarta.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // The user who is following another user
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // The user who is being followed
    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;

    public Follow() {
    }

    public Follow(int id, User follower, User followee) {
        this.id = id;
        this.follower = follower;
        this.followee = followee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
