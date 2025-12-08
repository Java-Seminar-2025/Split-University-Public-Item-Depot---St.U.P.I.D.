package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.*;
import com.StudenMarket.StUPID.Service.*;
import com.StudenMarket.StUPID.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/users")
@SessionAttributes("userRegistrationFirstStep")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UniversityService UniversityService;

    @ModelAttribute("userRegistrationFirstStep")
    public UserRegistrationFirstStepDTO userRegistrationFirstStep() {
        return new UserRegistrationFirstStepDTO();
    }

    // GET metode
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        List<University> universities = UniversityService.listAllUniversity();
        model.addAttribute("universities", universities);
        model.addAttribute("userRegistrationFirstStep", new UserRegistrationFirstStepDTO());
        return "register-first-step";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            return HelpMetods.redirectBasedOnRole(loggedUser);
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/get-courses")
    @ResponseBody
    public List<Course> getCoursesByUniversity(@RequestParam Long universityId) {
        return courseService.getCoursesByUniversity(universityId);
    }

    @GetMapping("/select-course")
    public String showCourseSelection(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            Model model) {
        List<Course> courses = courseService.getCoursesByUniversity(firstStepDTO.getUniversityId());
        model.addAttribute("courses", courses);
        return "register-course-selection";
    }

    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) throw new AppException("User doesn't exist!");

        User userDetails = userService.findUserById(currentUser.getId());
        model.addAttribute("userDetails", userDetails);

        return "users/profile";
    }

    @GetMapping("/welcome")
    public String welcomePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        return "users/welcome";
    }

    @GetMapping("/upload-profile-picture")
    public String showUploadProfilePictureForm(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            throw new AppException("User not logged in");
        }
        User userDetails = userService.findUserById(currentUser.getId());
        model.addAttribute("userDetails", userDetails);
        return "users/profile-picture-upload";
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            throw new AppException("User not logged in");
        }

        List<University> universities = UniversityService.listAllUniversity();
        model.addAttribute("userDetails", currentUser);
        model.addAttribute("universities", universities);

        return "users/edit-profile";
    }

    // POST metode
    @PostMapping("/register-first-step")
    public String processFirstStep(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            Model model) {
        try {
            userService.validateFirstStep().test(firstStepDTO);
            HelpMetods.validatePassword(firstStepDTO.getPassword());
            return "redirect:/users/select-course";

        } catch (AppException e) {
            List<University> universities = UniversityService.listAllUniversity();
            model.addAttribute("universities", universities);
            HelpMetods.addError(model, e.getMessage());
            return "register-first-step";
        }
    }

    @PostMapping("/complete-registration")
    public String completeRegistration(
            @ModelAttribute("userRegistrationFirstStep") UserRegistrationFirstStepDTO firstStepDTO,
            @RequestParam Long courseId,
            Model model) {
        try {
            userService.validateFirstStep().test(firstStepDTO);
            HelpMetods.validatePassword(firstStepDTO.getPassword());

            Course course = courseService.findById(courseId);

            User user = userService.prepareUserForSecondStep().apply(firstStepDTO);
            user.setPassword(HelpMetods.hashPassword(firstStepDTO.getPassword()));
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
            HttpSession session,
            Model model) {
        try {
            User user = userService.userLogin(usernameOrEmail);

            if (!HelpMetods.passwordMatches(password, user.getPassword())) {
                throw new AppException("Invalid password!");
            }

            session.setAttribute("loggedUser", user);
            return HelpMetods.redirectBasedOnRole(user);

        } catch (AppException e) {
            HelpMetods.addError(model, e.getMessage());
            return "login";
        }
    }

    @PostMapping("/upload-profile-picture")
    public String uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            throw new AppException("User not logged in");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new AppException("Only image files are allowed");
        }

        String fileName = currentUser.getId() + "_" +
                System.currentTimeMillis() +
                HelpMetods.getFileExtension(file.getOriginalFilename());

        Path uploadPath = Paths.get("./uploads/profile-pictures", fileName);
        Files.createDirectories(uploadPath.getParent());
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        String imagePath = "/uploads/profile-pictures/" + fileName;
        User updatedUser = userService.saveProfilePicture(currentUser.getId(), imagePath);

        session.setAttribute("loggedUser", updatedUser);
        return "redirect:/users/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @ModelAttribute("userDetails") User updatedUser,
            @RequestParam(value = "universityId", required = false) Long universityId,
            HttpSession session,
            Model model) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            throw new AppException("User not logged in");
        }

        currentUser.setName(updatedUser.getName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());

        if (universityId != null && universityId > 0) {
            List<Course> courses = courseService.getCoursesByUniversity(universityId);
            model.addAttribute("courses", courses);
            model.addAttribute("userDetails", currentUser);
            return "users/update-course-selection";
        }

        User updatedUserInDB = userService.UpdateUser(currentUser);
        session.setAttribute("loggedUser", updatedUserInDB);

        return "redirect:/users/profile";
    }

    @PostMapping("/update-course-selection")
    public String completeUpdate(
            @RequestParam Long courseId,
            HttpSession session,
            Model model) {
        try {
            User currentUser = (User) session.getAttribute("loggedUser");
            if (currentUser == null) {
                throw new AppException("User not logged in");
            }

            if (courseId == null) {
                throw new AppException("Course selection is required");
            }

            Course course = courseService.findById(courseId);
            if (course == null) {
                throw new AppException("Selected course does not exist");
            }

            currentUser.setCourse(course);

            User updatedUser = userService.UpdateUser(currentUser);
            session.setAttribute("loggedUser", updatedUser);
            return "redirect:/users/profile";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            return "users/update-course-selection";
        }
    }
}