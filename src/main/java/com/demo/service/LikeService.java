package com.demo.service;

import com.demo.model.Notification;
import com.demo.model.Post;
import com.demo.model.Like;
import com.demo.model.User;
import com.demo.repo.LikeRepo;
import com.demo.repo.PostRepo;
import com.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired NotificationService notificationService;

    @Autowired
    private PostRepo postRepo;

    public Like findById(int likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }

    public void deleteLikesByPost(Post post) {
        List<Like> likes = likeRepo.findByPost(post);
        likeRepo.deleteAll(likes);
    }
    public boolean isPostLikedByUser(Post post, User user) {
        return likeRepo.existsByPostAndUser(post, user);
    }

    public void toggleLike(int postId, String username) {
        Post post = postRepo.findById(postId).orElseThrow();
        User user = userRepo.findByUsername(username);

        Optional<Like> existing = likeRepo.findByPostAndUser(post, user);
        if (existing.isPresent()) {
            likeRepo.delete(existing.get());
        } else {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepo.save(like);

            if(post.getUser().getId() != user.getId()){
                Notification notification = new Notification();
                notification.setUser(post.getUser());
                notification.setTriggerUser(user);
                notification.setType(3);
                notification.setCreatedAt(LocalDateTime.now());
                notificationService.createNotification(notification);
            }
        }
    }


    public Like getLikedByCurrentUser(Post post, User user) {
        return likeRepo.findByPostAndUser(post,user).orElse(null);
    }
}
