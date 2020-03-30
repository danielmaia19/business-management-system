package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Email;
import com.danielmaia.businessmanagementsystem.Model.PasswordResetToken;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.EmailService;
import com.danielmaia.businessmanagementsystem.Service.PasswordResetTokenService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Display forgotPassword page
    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgot-password");
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        // Lookup user in database by e-mail
        User user = userService.findByEmail(userEmail);

        if (user == null) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password;
            String token = UUID.randomUUID().toString();
            PasswordResetToken theToken = new PasswordResetToken(token, user);
            passwordResetTokenService.saveToken(theToken);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            Email passwordResetEmail = new Email();
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setContent("To reset your password, click the link below:\n" + appUrl + ":8080/reset?token=" + token);

            emailService.sendSimpleMessage(passwordResetEmail);

            // Add success message to view
            redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("redirect:/forgot-password");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = passwordResetTokenService.findByToken(token).getUser();

        if (user != null) { // Token found in DB
            modelAndView.addObject("resetToken", token);
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("reset-password");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // Find the user associated with the reset token
        User user = passwordResetTokenService.findByToken(requestParams.get("token")).getUser();

        // This should always be non-null but we check just in case
        if (user != null) {

            // Set new password
            user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            // Save user
            userService.saveUser(user);

            passwordResetTokenService.delete(passwordResetTokenService.findByToken(requestParams.get("token")));

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:/login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("reset-password");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:/login");
    }







































    //@Autowired
    //private UserService userService;
    //
    //@Autowired
    //private EmailService emailService;
    //
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    //
    //@RequestMapping("/forgot-password")
    //public String index() {
    //    return "forgot-password";
    //}
    //
    //@RequestMapping(path = "/forgot-password", method = RequestMethod.POST)
    //private String sendEmailToken(Model modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request, BindingResult errors, RedirectAttributes redirectAttributes) {
    //
    //    User user = userService.findByEmail(userEmail);
    //
    //    if (user == null) {
    //        modelAndView.addAttribute("error", "We didn't find an account for that e-mail address.");
    //    }
    //
    //    String token = UUID.randomUUID().toString();
    //    user.setToken(token);
    //    userService.updateUser(user);
    //
    //    Email email = new Email();
    //    email.setSubject("BMS - Password Reset Request");
    //    email.setFrom("support@bms.com");
    //    email.setTo("dannymaia1992@gmail.com");
    //    email.setContent("User link below to reset password \r\n" + request.getScheme() + "://" + request.getServerName() + ":8080/reset?token=" + token);
    //    emailService.sendSimpleMessage(email);
    //
    //    modelAndView.addAttribute("token", token);
    //
    //    // Add success message to view
    //    redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to " + userEmail);
    //
    //    return "redirect:/forgot-password";
    //}
    //
    //// Display form to reset password
    //@RequestMapping(value = "/reset", method = RequestMethod.GET)
    //public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
    //
    //    User user = userService.findUserByToken(token);
    //
    //    if (user != null) { // Token found in DB
    //        modelAndView.addObject("resetToken", token);
    //    } else { // Token not found in DB
    //        modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
    //    }
    //
    //    modelAndView.setViewName("reset-password");
    //    return modelAndView;
    //}
    //
    //// Process reset password form
    //@PostMapping("/reset")
    //public String handlePasswordReset(@RequestParam("token") String token) {
    //
    //    System.out.println(token);
    //    return "reset-password";
    //
    //}


}