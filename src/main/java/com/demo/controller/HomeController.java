package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}

/*
Home
Search
Explore
Notifications
Create Post
Profile

Edit Profile
Logout

Update Post
Delete Post
Create Comment
Delete Comment
Like Post
Remove Like


Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.thymeleaf.exceptions.TemplateProcessingException: Could not parse as expression: "@{/unfollow/{profile.user.id}" (template: "profile" - line 183, col 23)] with root cause

 */