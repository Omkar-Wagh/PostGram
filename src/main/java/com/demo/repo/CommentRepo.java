package com.demo.repo;

import com.demo.model.Comment;
import com.demo.model.Post;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    int countByPost(Post post);

    List<Comment> findByPost(Post post);

    List<Comment> findByPostAndUser(Post post, User currentUser);
}

