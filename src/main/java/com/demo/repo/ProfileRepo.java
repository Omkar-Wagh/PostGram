package com.demo.repo;

import com.demo.model.Profile;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Profile findByUser(User user);

    Optional<Profile> findByUserId(int userId);
}
