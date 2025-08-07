package com.demo.service;

import com.demo.model.Post;
import com.demo.model.Profile;
import com.demo.model.User;
import com.demo.repo.PostRepo;
import com.demo.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private PostRepo postRepo;

    public String getImage(User user) {
        Profile profile = profileRepo.findByUser(user);
        if (profile != null && profile.getImageData() != null) {
            return Base64.getEncoder().encodeToString(profile.getImageData());
        }
        return null;
    }

    public Profile findByUser(User user) {
        return profileRepo.findByUser(user);
    }

    public void addProfile(Profile profile) throws IOException {
        profileRepo.save(profile);
    }

    public void updateProfile(int userId, MultipartFile imageFile, String name, String bio) throws IOException {
        Profile existingProfile = profileRepo.findByUserId(userId).orElse(null);

        if (imageFile != null && !imageFile.isEmpty() && existingProfile != null) {
            existingProfile.setImageName(imageFile.getOriginalFilename());
            existingProfile.setImageType(imageFile.getContentType());
            existingProfile.setImageData(imageFile.getBytes());
            existingProfile.setProfileName(name);
            existingProfile.setProfileBio(bio);
            profileRepo.save(existingProfile);
        }
    }

    public Profile getProfileByUser(User user) {
        return profileRepo.findByUser(user);
    }

    public Profile getProfileNameByUser(User user) {
        return profileRepo.findByUser(user);
    }
}

