package com.demo.aspects;


import com.demo.model.Profile;
import com.demo.model.User;
import com.demo.service.ProfileService;
import com.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ProfileCheckAspect {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Around("execution(* com.demo.controller..*(..))")
    public Object checkUserProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            // Not an HTTP request context, let it pass
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();

        // Skip certain URLs from profile check
        if (requestURI.equals("/") || requestURI.equals("/login") || requestURI.equals("/register") || requestURI.equals("/createProfile") || requestURI.equals("/submitProfile")) {
            return joinPoint.proceed();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String username = auth.getName();
            User user = userService.findByUsername(username);

            if (user == null) {
                return "redirect:/register";
            }

            Profile profile = profileService.getProfileByUser(user);
            if (profile == null) {
                return "redirect:/createProfile";
            }
        } else {
            return "redirect:/login";
        }

        return joinPoint.proceed();
    }
}
