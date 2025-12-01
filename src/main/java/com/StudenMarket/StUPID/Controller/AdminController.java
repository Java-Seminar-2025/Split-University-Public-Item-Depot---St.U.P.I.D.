package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;


    @GetMapping("/list-users")
    public String listUsers(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");

        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/users/login";
        }
        List<User> users = userService.findAllUsers();

        model.addAttribute("users", users);
        return "admin/list-users";
    }
}
