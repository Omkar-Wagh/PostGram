package com.demo.controller;

import com.demo.dto.FeedDTO;
import com.demo.dto.FollowDTO;
import com.demo.dto.NotificationDTO;
import com.demo.dto.SearchDTO;
import com.demo.model.Post;
import com.demo.model.Profile;
import com.demo.model.User;
import com.demo.model.Notification;
import com.demo.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {

        // Check if username already exists
        if (userService.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }

        // Check if email already exists
        if (userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email already in use");
        }

        // If there are any errors (field or duplicate), return to register form
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }



    // Profile Page
    @GetMapping("/profile/{userId}")
    public String viewProfile(@PathVariable int userId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findById(userId);
        if(user == null){
            return "redirect:/posts";
        }
        Profile profile = profileService.findByUser(user);

        if (profile != null && profile.getImageData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(profile.getImageData());
            profile.setBase64Image(base64Image);
        }

        int followerCount = followService.getFollowerCount(user);
        int followingCount = followService.getFollowingCount(user);

        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        User profileUser = userService.findById(userId);

        int followStatus = 0;

        if (loggedInUser.getId() != profileUser.getId()) {
            boolean alreadyFollowing = followService.isFollowing(loggedInUser, profileUser);
            followStatus = alreadyFollowing ? 1 : 2;
        }

        List<Post> posts = postService.getPostsByUser(user);
        List<FeedDTO> feedDTOS = new ArrayList<>();
        int postCount = 0;
        for (Post post : posts){
            int likeCount = postService.getPostLikeCount(post);
            int commentCount = postService.getPostCommentCount(post);

            if (post != null && post.getImageData() != null) {
                String base64Image = Base64.getEncoder().encodeToString(post.getImageData());
                post.setBase64Image(base64Image);
            }

            FeedDTO dto = new FeedDTO(
                    post.getId(),
                    post.getImageName(),
                    post.getImageType(),
                    post.getBase64Image(),
                    likeCount,
                    commentCount
            );
            feedDTOS.add(dto);
            postCount++;
        }
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        model.addAttribute("followStatus", followStatus);
        model.addAttribute("profileUser", user);
        model.addAttribute("currentUser", loggedInUser);
        model.addAttribute("profile", profile);
        model.addAttribute("postCount",postCount);
        model.addAttribute("feedList", feedDTOS);

        return "profile";
    }

    // Profile Creation
    @GetMapping("/createProfile")
    public String showProfileForm(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(userDetails.getUsername());
        if (user == null) {
            return "redirect:/register";
        }

        Profile profile = profileService.getProfileByUser(user);
        if (profile != null) {
            return "redirect:/posts";
        }

        return "profileForm";
    }

    @PostMapping("/submitProfile")
    public String submitProfile(
            @RequestParam("profileName") String profileName,
            @RequestParam("profileBio") String profileBio,
            @RequestParam("profileImage") MultipartFile profileImage,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        User user = userService.findByUsername(userDetails.getUsername());

        Profile profile = new Profile();
        if (!profileImage.isEmpty()) {
            profile.setProfileName(profileName);
            profile.setProfileBio(profileBio);
            profile.setUser(user);
            profile.setImageData(profileImage.getBytes());
            profile.setImageName(profileImage.getOriginalFilename());
            profile.setImageType(profileImage.getContentType());
            }
        profileService.addProfile(profile);
        return "redirect:/profile/" + user.getId();
    }


    // Update Profile
    @GetMapping("/editProfile")
    public String updateProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Profile profile = profileService.getProfileByUser(user);
        profile.setBase64Image(profileService.getImage(user));
        model.addAttribute("profile", profile);
        return "updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("profileName") String profileName,
            @RequestParam("profileBio") String profileBio,
            @RequestPart("imageFile") MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        profileService.updateProfile(user.getId(), imageFile, profileName, profileBio);

        return "redirect:/profile/" + user.getId();
    }


    // Following and Followers List
    @GetMapping("/profile/{id}/followers")
    public String getFollowersPage(@PathVariable("id") int userId,
                                   Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        User profileUser = userService.findById(userId);
        List<User> followers = followService.getFollowers(profileUser);
        List<FollowDTO> followDTOs = new ArrayList<>();


        for (User user : followers){
                Profile profile = profileService.getProfileByUser(user);
                int followStatus = 0;
                if (loggedInUser.getId() != user.getId()) {
                    boolean alreadyFollowing = followService.isFollowing(loggedInUser, user);
                    followStatus = alreadyFollowing ? 1 : 2;
                }
                FollowDTO dto = new FollowDTO(
                        user.getId(),
                        profileService.getImage(user),
                        user.getUsername(),
                        profile.getProfileName(),
                        followStatus
                );
                if (user.getId() == loggedInUser.getId()) {
                    followDTOs.add(0, dto);
                } else {
                    followDTOs.add(dto);
                }
        }

        model.addAttribute("followValue",1);
        model.addAttribute("follow", followDTOs);
        return "followList";
    }

    @GetMapping("/profile/{id}/following")
    public String getFollowingPage(@PathVariable("id") int userId,
                                   Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        User profileUser = userService.findById(userId);
        List<User> following = followService.getFollowing(profileUser);
        List<FollowDTO> followDTOs = new ArrayList<>();

        for (User user : following){
            int followStatus = 0;
            Profile profile = profileService.getProfileByUser(user);
            if (loggedInUser.getId() != user.getId()) {
                boolean alreadyFollowing = followService.isFollowing(loggedInUser, user);
                followStatus = alreadyFollowing ? 1 : 2;
            }
            FollowDTO dto = new FollowDTO(
                    user.getId(),
                    profileService.getImage(user),
                    user.getUsername(),
                    profile.getProfileName(),
                    followStatus
            );
            if(user.getId() == loggedInUser.getId()){
                followDTOs.add(0,dto);
            }else{
                followDTOs.add(dto);
            }
        }

        model.addAttribute("followValue",2);
        model.addAttribute("follow", followDTOs);
        return "followList";
    }

    @GetMapping("/notification")
    public String getNotifications(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Notification> notifications = notificationService.getNotificationsByUser(user);
        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO dto = new NotificationDTO(
                    notification.getTriggerUser().getId(),
                    profileService.getImage(notification.getTriggerUser()),
                    profileService.getProfileByUser(notification.getTriggerUser()).getProfileName(),
                    notification.getType(),
                    postService.getTimeAgo(notification.getCreatedAt())
            );
            notificationDTOs.add(dto);

            if (!notification.isSeen()) {
                notification.setSeen(true);
            }
        }

        Collections.reverse(notificationDTOs);

        notificationService.saveAll(notifications);

        model.addAttribute("notifications", notificationDTOs);
        return "notification";
    }


    @GetMapping("/find")
    public String getSearch(){
        return "search";
    }

    @GetMapping("/search/{keywords}")
    @ResponseBody
    public List<SearchDTO> getSearchResult(@PathVariable String keywords, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        List<User> searchUser = new ArrayList<>(userService.getUserBySearch(keywords));
        List<SearchDTO> searchDTOs = new ArrayList<>();

        for (User user : searchUser) {
            Profile userProfile = profileService.getProfileByUser(user);
            if (userProfile != null) {
                int followStatus = 0;
                if (loggedInUser.getId() != user.getId()) {
                    boolean alreadyFollowing = followService.isFollowing(loggedInUser, user);
                    followStatus = alreadyFollowing ? 1 : 2;
                }
                SearchDTO dto = new SearchDTO(
                        user.getId(),
                        profileService.getImage(user),
                        userProfile.getProfileName(),
                        user.getUsername(),
                        followStatus
                );
                searchDTOs.add(dto);
            }
        }
        return searchDTOs;
    }

    @GetMapping("/errorPage")
    public String getErrorPage(){
        return "errorPage";
    }

}
