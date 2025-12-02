package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.Role;
import com.StudenMarket.StUPID.Entity.Rules;
import com.StudenMarket.StUPID.Service.RulesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RulesService rulesService;


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

    @GetMapping("/list-rules")
    public String listRules(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/users/login";
        }

        List<Rules> rules = rulesService.ListAll();
        model.addAttribute("rules", rules);
        return "admin/list-rules";
    }

    @GetMapping("/add-rule")
    public String showRuleForm(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");

        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/users/login";
        }

        model.addAttribute("rule", new Rules());
        return "admin/add-rule";
    }

    @PostMapping("/saveRule")
    public String saveRule(HttpSession session, Rules rule) {
        User currentUser = (User) session.getAttribute("loggedUser");

        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/users/login";
        }

        rule.setCreated(Date.from(Instant.now()));
        rulesService.CreateRule(rule);
        return "redirect:/admin/list-rules";
    }
}
