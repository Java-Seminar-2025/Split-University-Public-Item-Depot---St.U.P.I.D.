package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.University;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Repository.CourseRepository;
import com.StudenMarket.StUPID.Repository.UniversityRepository;
import com.StudenMarket.StUPID.Service.*;
import com.StudenMarket.StUPID.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UniversityService universityService;

    private Function<String, String> hashPassword()
    {
        return password -> passwordEncoder.encode(password);
    }

    private BiPredicate<String, String> passwordMatches()
    {
        return (password, hashedPassword) -> passwordEncoder.matches(password, hashedPassword);
    }

    private final Consumer<Model> addEmptyUser =
            model -> model.addAttribute("user", new User());

    private final BiConsumer<Model, String> addError =
            (model, error) -> model.addAttribute("errorMessage", error);

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        addEmptyUser.accept(model);
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        List<University> universities = universityService.listAllUniversity();
        model.addAttribute("universities", universities);
        addEmptyUser.accept(model);
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            user.setPassword(hashPassword().apply(user.getPassword()));
            userService.userRegister(user);
            return "redirect:/";
        } catch (AppException e) {
            addError.accept(model, e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String usernameOrEmail,
            @RequestParam String password,
            Model model)
    {
        try {
            User user = userService.userLogin(usernameOrEmail);

            if (!passwordMatches().test(password, user.getPassword()))
            {
                throw new AppException("Invalid password!");
            }
            return "redirect:/";

        } catch (AppException e) {
            addError.accept(model, e.getMessage());
            return "login";
        }
    }
}