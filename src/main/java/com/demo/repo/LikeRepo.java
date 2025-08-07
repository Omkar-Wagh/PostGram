package com.demo.repo;

import com.demo.model.Like;
import com.demo.model.Post;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like,Integer> {
    int countByPost(Post post);
    List<Like> findByPost(Post post);
    Optional<Like> findByPostAndUser(Post post, User user);
    boolean existsByPostAndUser(Post post, User user);


}
