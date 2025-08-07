package com.demo.service;

import com.demo.model.Post;
import com.demo.model.Profile;
import com.demo.model.User;
import com.demo.repo.CommentRepo;
import com.demo.repo.LikeRepo;
import com.demo.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private CommentRepo commentRepo;

    public List<Post> getPostsByUser(User user) {
        return postRepo.findByUser(user);
    }

    public int getPostLikeCount(Post post) {
        return likeRepo.countByPost(post);
    }

    public int getPostCommentCount(Post post) {
        return commentRepo.countByPost(post);
    }

    public Post findById(int postId) {
        return postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void savePost(Post post, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            post.setImageName(imageFile.getOriginalFilename());
            post.setImageType(imageFile.getContentType());
            post.setImageData(imageFile.getBytes());
        }
        postRepo.save(post);
    }

    public void deletePost(int postId) {
        postRepo.deleteById(postId);
    }

    public Post getPostById(int postId) {
        return postRepo.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    public String getTimeAgo(LocalDateTime createdAt) {
        Duration duration = Duration.between(createdAt, LocalDateTime.now());

        if (duration.toMinutes() < 1) return "Just now";
        if (duration.toMinutes() < 60) return duration.toMinutes() + " minutes ago";
        if (duration.toHours() < 24) return duration.toHours() + " hours ago";
        if (duration.toDays() == 1) return "Yesterday";
        if (duration.toDays() < 7) return duration.toDays() + " days ago";
        if (duration.toDays() < 30) return (duration.toDays() / 7) + " weeks ago";
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
}

