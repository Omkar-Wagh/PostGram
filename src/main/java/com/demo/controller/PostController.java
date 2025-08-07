package com.demo.controller;

import com.demo.dto.CommentDTO;
import com.demo.dto.FeedDTO;
import com.demo.dto.PostDTO;
import com.demo.model.*;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("")
public class PostController {
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

    // Home page Displaying the post of Following Accounts
    //---------------Home Page Creation---------------//
    @GetMapping("/posts")
    public String getPost(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        Profile profile = profileService.getProfileByUser(user);

        List<User> unknownUser = new ArrayList<>(userService.getUser());
        List<User> followList = new ArrayList<>(followService.getFollowing(user));
        followList.add(user);

        List<PostDTO> postDTOs = new ArrayList<>();

        for (User followedUser : followList) {
            List<Post> posts = postService.getPostsByUser(followedUser);
            for (Post post : posts) {
                int likeCount = postService.getPostLikeCount(post);
                int commentCount = postService.getPostCommentCount(post);

                String userProfileImage = profileService.getImage(post.getUser());

                String base64Image = Base64.getEncoder().encodeToString(post.getImageData());
                post.setBase64Image(base64Image);

                String timeAgo = postService.getTimeAgo(post.getCreatedAt());
                Profile postUserprofile = profileService.getProfileNameByUser(post.getUser());

                Like like = likeService.getLikedByCurrentUser(post,user);

                int likedByCurrentUser = 0;

                if(like != null){
                    likedByCurrentUser = 1;
                }else{
                    likedByCurrentUser = 2;
                }

                int postStatus = 0;
                if(user.getId() == post.getUser().getId()){
                    postStatus = 1;
                }
                PostDTO dto = new PostDTO(
                        post.getId(),
                        post.getCaption(),
                        post.getImageName(),
                        post.getImageType(),
                        post.getBase64Image(),
                        post.getCreatedAt(),
                        timeAgo,
                        post.getUser().getUsername(),
                        postUserprofile.getProfileName(),
                        userProfileImage,
                        likeCount,
                        commentCount,
                        post.getUser().getId(),
                        likedByCurrentUser,
                        postStatus
                );

                postDTOs.add(dto);
            }
        }

        postDTOs.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        for (User newUser : unknownUser) {
            if(newUser != user){
                List<Post> posts = postService.getPostsByUser(newUser);
                for (Post post : posts) {
                    int likeCount = postService.getPostLikeCount(post);
                    int commentCount = postService.getPostCommentCount(post);

                    String userProfileImage = profileService.getImage(post.getUser());

                    String base64Image = Base64.getEncoder().encodeToString(post.getImageData());
                    post.setBase64Image(base64Image);

                    String timeAgo = postService.getTimeAgo(post.getCreatedAt());
                    Profile postUserprofile = profileService.getProfileNameByUser(post.getUser());

                    Like like = likeService.getLikedByCurrentUser(post,user);

                    int likedByCurrentUser = 0;
                    if(like != null){
                        likedByCurrentUser = 1;
                    }else{
                        likedByCurrentUser = 2;
                    }
                    int postStatus = 0;
                    if(user.getId() == post.getUser().getId()){
                        postStatus = 1;
                    }
                    PostDTO dto = new PostDTO(
                            post.getId(),
                            post.getCaption(),
                            post.getImageName(),
                            post.getImageType(),
                            post.getBase64Image(),
                            post.getCreatedAt(),
                            timeAgo,
                            post.getUser().getUsername(),
                            postUserprofile.getProfileName(),
                            userProfileImage,
                            likeCount,
                            commentCount,
                            post.getUser().getId(),
                            likedByCurrentUser,
                            postStatus
                    );

                    postDTOs.add(dto);
                }
            }
        }

        // Unique Posts
//        postDTOs = postDTOs.stream()
//                .distinct()
//                .collect(Collectors.toList());

        int currentUserId = user.getId();
        model.addAttribute("posts", postDTOs);
        model.addAttribute("user" , user);
        return "post";
    }


    //---------------Post Creation---------------//

    @GetMapping("/createPost")
    public String createPost() {
        return "addPost";
    }

    @PostMapping("/submitPost")
    public String savePost(@RequestParam("caption") String caption,
                           @RequestPart("imageFile") MultipartFile imageFile,
                           @AuthenticationPrincipal UserDetails userDetails) {

        try {
            User currentUser = userService.findByUsername(userDetails.getUsername());
            Post post = new Post();
            post.setCaption(caption);
            post.setUser(currentUser);
            postService.savePost(post, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/posts";
    }



    @PostMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable("postId") int postId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(postId);
        if (post != null && post.getUser().getId() == currentUser.getId()) {
            // 1. Delete all likes associated with this post
            likeService.deleteLikesByPost(post);

            // 2. Delete all comments associated with this post
            commentService.deleteCommentsByPost(post);

            // 3. Delete the post itself
            postService.deletePost(postId);
        }

        return "redirect:/posts";
    }

    //---------------Post Update---------------//

    @GetMapping("/editPost/{postId}")
    public String showEditForm(@PathVariable("postId") int postId,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(postId);
        post.setBase64Image(Base64.getEncoder().encodeToString(post.getImageData()));

        if (post != null && post.getUser().getId() == currentUser.getId()) {
            Profile profile = profileService.getProfileByUser(currentUser);
            int likeCount = postService.getPostLikeCount(post);
            int commentCount = postService.getPostCommentCount(post);
            Like like = likeService.getLikedByCurrentUser(post,currentUser);

            int likedByCurrentUser = 0;

            if(like != null){
                likedByCurrentUser = 1;
            }else{
                likedByCurrentUser = 2;
            }

            PostDTO dto = new PostDTO(
                    post.getId(),
                    post.getCaption(),
                    post.getImageName(),
                    post.getImageType(),
                    post.getBase64Image(),
                    post.getCreatedAt(),
                    postService.getTimeAgo(post.getCreatedAt()),
                    post.getUser().getUsername(),
                    profile.getProfileName(),
                    profileService.getImage(currentUser),
                    likeCount,
                    commentCount,
                    post.getUser().getId(),
                    likedByCurrentUser,
                    1
            );
            model.addAttribute("post", dto);
            return "updatePost";
        }

        return "redirect:/singlePost/" + postId;
    }

    @PostMapping("/updatePost/{postId}")
    public String updatePost(@PathVariable("postId") int postId,
                             @RequestParam("caption") String caption,
                             @RequestPart("postImage") MultipartFile imageFile,
                             @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.findByUsername(userDetails.getUsername());
            Post existingPost = postService.findById(postId);

            if (existingPost != null && existingPost.getUser().getId() == currentUser.getId()) {
                existingPost.setCaption(caption);
                postService.savePost(existingPost,imageFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/errorPage";
        }

        return "redirect:/singlePost/" + postId;
    }

    //---------------Comment Creation---------------//

    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable("postId") int postId,
                             @RequestParam("commentText") String commentText,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(postId);

        if (post != null && currentUser != null && !commentText.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(currentUser);
            comment.setCommentText(commentText);
            commentService.saveComment(comment);
            // Notification Creation
            if(post.getUser().getId() != currentUser.getId()){
                Notification notification = new Notification();
                notification.setUser(post.getUser());
                notification.setTriggerUser(currentUser);
                notification.setType(2);
                notification.setCreatedAt(LocalDateTime.now());
                notificationService.createNotification(notification);
            }
        }

        return "redirect:/singlePost/" + postId;
    }

    @PostMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable("commentId") int commentId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Comment comment = commentService.findById(commentId);
        int postId = comment.getPost().getId();

        if (comment != null) {
            String loggedInUsername = userDetails.getUsername();
            if (comment.getUser().getUsername().equals(loggedInUsername)) {
                commentService.deleteComment(commentId);
            } else {
            }
        }

        return "redirect:/singlePost/" + postId;
    }


    //---------------Like Creation---------------//
    @PostMapping("/post/{postId}/like")
    public String toggleLike(@PathVariable int postId,@AuthenticationPrincipal UserDetails userDetails) {
        likeService.toggleLike(postId, userDetails.getUsername());
        return "redirect:/singlePost/" + postId;
    }

    //---------------Follow Creation---------------//

    @PostMapping("/follow/{followId}")
    public String followUser(@PathVariable("followId") int followId,
                             @AuthenticationPrincipal UserDetails userDetails) {

        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        User profileUser = userService.findById(followId);

        if (loggedInUser.getId() != profileUser.getId()) {
            boolean alreadyFollowing = followService.isFollowing(loggedInUser, profileUser);
            if (!alreadyFollowing) {
                // Make Follow REQUEST
                Follow follow = new Follow();
                follow.setFollower(loggedInUser);
                follow.setFollowee(profileUser);
                followService.save(follow);
                Notification notification = new Notification();
                notification.setUser(profileUser);
                notification.setTriggerUser(loggedInUser);
                notification.setType(1);
                notification.setCreatedAt(LocalDateTime.now());
                notificationService.createNotification(notification);
            }
        }

        return "redirect:/profile/" + followId;
    }


    @PostMapping("/unfollow/{followId}")
    public String unfollowUser(@PathVariable("followId") int followId,
                               @AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        User profileUser = userService.findById(followId);

        // Prevent user from unfollowing themselves
        if (loggedInUser.getId() != profileUser.getId()) {
            boolean alreadyFollowing = followService.isFollowing(loggedInUser, profileUser);
            if (alreadyFollowing) {
                Follow follow = followService.getFollowRelation(loggedInUser, profileUser);
                if (follow != null) {
                    followService.deleteFollow(follow);
                }
            }
        }

        return "redirect:/profile/" + followId;
    }


    //---------------Single-Post Creation---------------//

    @GetMapping("singlePost/{postId}")
    public String singlePost(@PathVariable("postId") int postId,Model model, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        User currentUser = userService.findByUsername(username);

        Post post = postService.getPostById(postId);
        User user = post.getUser();
        Profile profile = profileService.getProfileByUser(user);
        List<Comment> comments = commentService.getCommentsByPost(post);

        List<PostDTO> postDTOs = new ArrayList<>();

        int likeCount = postService.getPostLikeCount(post);
        int commentCount = postService.getPostCommentCount(post);

        if (post != null && post.getImageData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(post.getImageData());
            post.setBase64Image(base64Image);
        }

        String timeAgo = postService.getTimeAgo(post.getCreatedAt());
        String userProfileImage = profileService.getImage(post.getUser());

        Like like = likeService.getLikedByCurrentUser(post,currentUser);

        int likedByCurrentUser = 0;
        if(like != null){
            likedByCurrentUser = 1;
        }else{
            likedByCurrentUser = 2;
        }
        int postStatus = 0;
        if(currentUser.getId() == post.getUser().getId()){
            postStatus = 1;
        }
        PostDTO dto = new PostDTO(
                post.getId(),
                post.getCaption(),
                post.getImageName(),
                post.getImageType(),
                post.getBase64Image(),
                post.getCreatedAt(),
                timeAgo,
                post.getUser().getUsername(),
                profile.getProfileName(),
                userProfileImage,
                likeCount,
                commentCount,
                post.getUser().getId(),
                likedByCurrentUser,
                postStatus
        );

        postDTOs.add(dto);

        List<CommentDTO> commentDTOs = new ArrayList<>();

        for(Comment c : comments){
                User userCommented = c.getUser();
                Post userPost = c.getPost();
                Profile userProfile = profileService.getProfileByUser(userCommented);

                int commentStatus = 0;
                if(currentUser == userCommented){
                    commentStatus = 1;
                }else{
                    commentStatus = 2;
                }
                CommentDTO dtoS = new CommentDTO(
                        c.getId(),
                        c.getUser().getId(),
                        profileService.getImage(userCommented),
                        userProfile.getProfileName(),
                        userCommented.getUsername(),
                        postService.getTimeAgo(c.getCreatedAt()),
                        c.getCommentText(),
                        commentStatus
                );
                if(c.getUser() == currentUser){
                    commentDTOs.add(0,dtoS);
                }else{
                    commentDTOs.add(dtoS);
                }
        }

        model.addAttribute("posts",postDTOs);
        model.addAttribute("comments",commentDTOs);
        return "singlePost";
    }

    @GetMapping("userPost/{postId}")
    public String getAllPostByUser(@PathVariable int postId, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        User currentUser = userService.findByUsername(username);

        Post selectedPost = postService.getPostById(postId);
        User user = null;

        if (selectedPost != null) {
            user = selectedPost.getUser();
        }

        if (user == null) {
            return "errorPage";
        }

        Profile profile = profileService.getProfileByUser(user);

        List<PostDTO> postDTOs = new ArrayList<>();
        List<Post> posts = postService.getPostsByUser(user);

        for (Post post : posts) {
                int likeCount = postService.getPostLikeCount(post);
                int commentCount = postService.getPostCommentCount(post);

                String profileImage = profileService.getImage(post.getUser());

                if (post != null && post.getImageData() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(post.getImageData());
                    post.setBase64Image(base64Image);
                }
                String timeAgo = postService.getTimeAgo(post.getCreatedAt());
                String userProfileImage = profileService.getImage(post.getUser());
                Profile postUserprofile = profileService.getProfileNameByUser(post.getUser());

                Like like = likeService.getLikedByCurrentUser(post,currentUser);

                int likedByCurrentUser = 0;
                if(like != null){
                    likedByCurrentUser = 1;
                }else{
                    likedByCurrentUser = 2;
                }
                int postStatus = 0;
                if(currentUser.getId() == post.getUser().getId()){
                    postStatus = 1;
                }

                PostDTO dto = new PostDTO(
                        post.getId(),
                        post.getCaption(),
                        post.getImageName(),
                        post.getImageType(),
                        post.getBase64Image(),
                        post.getCreatedAt(),
                        timeAgo,
                        post.getUser().getUsername(),
                        postUserprofile.getProfileName(),
                        userProfileImage,
                        likeCount,
                        commentCount,
                        post.getUser().getId(),
                        likedByCurrentUser,
                        postStatus
                );

                if (post.getId() == selectedPost.getId()) {
                    // Add at the start
                    postDTOs.add(0, dto);
                } else {
                    // Add at the end
                    postDTOs.add(dto);
                }
        }

        model.addAttribute("profile", profile);
        model.addAttribute("posts", postDTOs);
        return "post";
    }

    @GetMapping("/feed")
    public String getFeed(Model model){
        List<Post> posts = postService.getAllPosts();
        List<FeedDTO> feedDTOS = new ArrayList<>();
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
        }

        Collections.shuffle(feedDTOS);
        model.addAttribute("feedList", feedDTOS);
        return "explore";
    }
}
