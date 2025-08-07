package com.demo.service;

import com.demo.model.Follow;
import com.demo.model.User;
import com.demo.repo.FollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepo followRepo;

    public List<User> getUsersFollowedByCurrentUser(User currentUser) {
        return followRepo.findFollowedUsersByUser(currentUser);
    }
    public int getFollowerCount(User user) {
        return followRepo.countByFollowee(user);
    }

    public int getFollowingCount(User user) {
        return followRepo.countByFollower(user);
    }

    public List<User> getFollowers(User user) {
        List<Follow> follows = followRepo.findByFollowee(user);
        return follows.stream().map(Follow::getFollower).toList();
    }

    public List<User> getFollowing(User user) {
        List<Follow> follows = followRepo.findByFollower(user);
        return follows.stream().map(Follow::getFollowee).toList();
    }

    public void save(Follow follow) {
        followRepo.save(follow);
    }

    public boolean isFollowing(User follower, User followee) {
        return followRepo.existsByFollowerAndFollowee(follower, followee);
    }

    public Follow getFollowRelation(User follower, User followee) {
        return followRepo.findByFollowerAndFollowee(follower, followee);
    }

    public void deleteFollow(Follow follow) {
        followRepo.delete(follow);
    }

}