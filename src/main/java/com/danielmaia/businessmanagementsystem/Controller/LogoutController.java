package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    // Show login page when this is called

    /**
     * Show login page when user logs out.
     * @param request
     * @param response
     * @param authentication
     * @return Redirected to the login page with a message of success.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) { new SecurityContextLogoutHandler().logout(request, response, authentication); }
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

}
