package com.demo.service;

import com.demo.model.Comment;
import com.demo.model.Post;
import com.demo.model.User;
import com.demo.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public void saveComment(Comment comment) {
        commentRepo.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepo.findByPost(post);
    }

    public Comment findById(int id) {
        return commentRepo.findById(id).orElse(null);
    }

    public void deleteComment(int id) {
        commentRepo.deleteById(id);
    }
    public void deleteCommentsByPost(Post post) {
        List<Comment> comments = commentRepo.findByPost(post);
        commentRepo.deleteAll(comments);
    }


    public List<Comment> getCommentsByPostAndUser(Post post, User currentUser) {
        return commentRepo.findByPostAndUser(post,currentUser);
    }
}
