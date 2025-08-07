package com.demo.repo;

import com.demo.model.Follow;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow,Integer> {

    @Query("SELECT f.followee FROM Follow f WHERE f.follower = :user")
    List<User> findFollowedUsersByUser(@Param("user") User user);

    int countByFollowee(User user);   // Users who follow the given user

    int countByFollower(User user);   // Users the given user follows

    // Users who follow the given user (followers)
    List<Follow> findByFollowee(User user);

    // Users whom the given user follows (following)
    List<Follow> findByFollower(User user);

    boolean existsByFollowerAndFollowee(User follower, User followee);

    Follow findByFollowerAndFollowee(User follower, User followee);
}
