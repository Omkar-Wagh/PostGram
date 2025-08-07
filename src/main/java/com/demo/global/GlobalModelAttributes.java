package com.demo.global;

import com.demo.model.User;
import com.demo.service.NotificationService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.demo.model.Notification;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addNotificationsToModel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null){
            int notificationCount = 0;
            User currentUser = userService.findByUsername(userDetails.getUsername());
            List<Notification> notifications = notificationService.getUnseenNotificationsByUser(currentUser);
            for(Notification n : notifications){
                notificationCount++;
            }
            model.addAttribute("notificationCount",notificationCount);
        }
    }
    @ModelAttribute
    public void addUserIdToModel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null) {
            User trueUser = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("trueUser", trueUser);
        }
    }
}
