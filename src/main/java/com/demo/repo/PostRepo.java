package com.demo.repo;

import com.demo.model.Post;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    Post getPostById(int postId);
}
