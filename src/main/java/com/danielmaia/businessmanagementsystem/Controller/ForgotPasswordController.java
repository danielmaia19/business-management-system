package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Mail;
import com.danielmaia.businessmanagementsystem.Model.PasswordResetToken;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.EmailService;
import com.danielmaia.businessmanagementsystem.Service.PasswordResetTokenService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ParseException, MessagingException, IOException {

        // Lookup user in database by e-mail
        User user = userService.findByEmail(userEmail);

        if (user == null) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password;
            String token = UUID.randomUUID().toString();
            PasswordResetToken theToken = new PasswordResetToken(token, user);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 24);
            Date date1=new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateFormat.format(calendar.getTime()));
            theToken.setExpiryDate(date1);

            passwordResetTokenService.saveToken(theToken);

            String url = request.getScheme() + "://" + request.getServerName() + ":8080/reset?token=" + token;
            Mail mail = new Mail();
            mail.setFrom("yourmailid@email.com");
            mail.setTo(user.getEmail());
            mail.setSubject("Email with Spring boot and thymeleaf template!");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", user.getFullName());
            model.put("url", url);
            mail.setProps(model);
            emailService.sendEmail(mail);

            // Add success message to view
            redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("redirect:/forgot-password");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) throws ParseException {

        Date date = passwordResetTokenService.findByToken(token).getExpiryDate();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateFormat.format(date)).before(new Date())) {
            passwordResetTokenService.delete(passwordResetTokenService.findByToken(token));
            modelAndView.addObject("invalidToken","Token is now Invalid, create a new Token");
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        User user = passwordResetTokenService.findByToken(token).getUser();

        if (user != null) { // Token found in DB
            modelAndView.addObject("resetToken", token);
            modelAndView.addObject("id", user.getUser_id());
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("reset-password");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) throws ParseException {

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
            redir.addFlashAttribute("message", "You have successfully reset your password.");

            modelAndView.setViewName("redirect:/login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("reset-password");
        }

        return modelAndView;
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:/login");
    }

}