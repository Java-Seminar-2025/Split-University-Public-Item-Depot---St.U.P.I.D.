package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.*;
import com.StudenMarket.StUPID.Service.*;
import com.StudenMarket.StUPID.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

@Controller
@RequestMapping("/users")
@SessionAttributes("userRegistrationFirstStep")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UniversityService UniversityService;

    private Function<String, String> hashPassword()
    {
        return password -> passwordEncoder.encode(password);
    }

    private BiPredicate<String, String> passwordMatches()
    {
        return (password, hashedPassword) -> passwordEncoder.matches(password, hashedPassword);
    }

    private final BiConsumer<Model, String> addError =
            (model, error) -> model.addAttribute("errorMessage", error);


    public void validatePassword(String password) {
        Optional.ofNullable(password)
                .filter(p -> p.matches("^(?=.*[A-Z])(?=.*\\d).{6,}$"))
                .orElseThrow(() -> new AppException("Password must contain at least one uppercase letter, one number, and at least 6 characters"));
    }

    @ModelAttribute("userRegistrationFirstStep")
    public UserRegistrationFirstStepDTO userRegistrationFirstStep()
    {
        return new UserRegistrationFirstStepDTO();
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model)
    {
        List<University> universities = UniversityService.listAllUniversity();
        model.addAttribute("universities", universities);

        model.addAttribute("userRegistrationFirstStep", new UserRegistrationFirstStepDTO());

        return "register-first-step";
    }

    @GetMapping("/login")
    public String showLoginForm()
    {
        return "login";
    }

    @GetMapping("/get-courses")
    @ResponseBody
    public List<Course> getCoursesByUniversity(@RequestParam Long universityId)
    {
        return courseService.getCoursesByUniversity(universityId);
    }

    @GetMapping("/select-course")
    public String showCourseSelection(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            Model model)
    {
        List<Course> courses = courseService.getCoursesByUniversity(firstStepDTO.getUniversityId());
        model.addAttribute("courses", courses);
        return "register-course-selection";
    }

    @PostMapping("/register-first-step")
    public String processFirstStep(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            Model model)
    {
        try {
            userService.validateFirstStep().test(firstStepDTO);
            validatePassword(firstStepDTO.getPassword());

            return "redirect:/users/select-course";

        } catch (AppException e) {
            List<University> universities = UniversityService.listAllUniversity();
            model.addAttribute("universities", universities);

            addError.accept(model, e.getMessage());
            return "register-first-step";
        }
    }

    @PostMapping("/complete-registration")
    public String completeRegistration(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            @RequestParam Long courseId,
            Model model)
    {
        try {
            userService.validateFirstStep().test(firstStepDTO);
            validatePassword(firstStepDTO.getPassword());

            Course course = courseService.findById(courseId);

            User user = userService.prepareUserForSecondStep().apply(firstStepDTO);
            user.setPassword(hashPassword().apply(firstStepDTO.getPassword()));
            userService.completeUserRegistration().apply(user, course);

            return "redirect:/users/login";

        } catch (AppException e) {
            List<Course> courses = courseService.getCoursesByUniversity(firstStepDTO.getUniversityId());
            model.addAttribute("courses", courses);
            model.addAttribute("errorMessage", e.getMessage());
            return "register-course-selection";
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