package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Exception.AppException;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class HelpMetods {
    private static PasswordEncoder passwordEncoder;

    public HelpMetods(PasswordEncoder passwordEncoder) {
        HelpMetods.passwordEncoder = passwordEncoder;
    }

    public static String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static void addError(Model model, String error) {
        model.addAttribute("errorMessage", error);
    }

    public static void validatePassword(String password) {
        Optional.ofNullable(password)
                .filter(p -> p.matches("^(?=.*[A-Z])(?=.*\\d).{6,}$"))
                .orElseThrow(() -> new AppException("Password must contain at least one uppercase letter, one number, and at least 6 characters"));
    }

    public static String redirectBasedOnRole(User user) {
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin/list-users";
            default:
                return "redirect:/users/sell-posts";
        }
    }

    public static String getFileExtension(String filename) {
        return filename != null && filename.contains(".") ?
                filename.substring(filename.lastIndexOf(".")) :
                ".jpg";
    }

    public static Optional<User> validateLoggedInUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedUser");
        return Optional.ofNullable(currentUser)
                .or(() -> {
                    throw new AppException("User not logged in");
                });
    }
}