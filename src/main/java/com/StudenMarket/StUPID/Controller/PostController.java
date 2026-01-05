package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.Category;
import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;

    //get

    @GetMapping("/sell-posts")
    public String showSellPosts(HttpSession session, Model model)
    {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<Post> sellPosts = postService.listAllSellPosts(currentUser);
        model.addAttribute("posts", sellPosts);
        return "users/sell-posts";
    }

    @GetMapping("/create-sell-post")
    public String showCreateSellPostForm(HttpSession session, Model model)
    {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        return "users/create-sell-post";
    }

    @GetMapping("/free-posts")
    public String showFreePosts(HttpSession session, Model model)
    {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<Post> freePosts = postService.listAllFreePosts(currentUser);
        model.addAttribute("posts", freePosts);
        return "users/free-posts";
    }

    @GetMapping("/create-free-posts")
    public String showCreateFreePostForm(HttpSession session, Model model)
    {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        return "users/create-free-posts";
    }

    @GetMapping("/seeking-posts")
    public String showSeekingPosts(HttpSession session, Model model)
    {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<Post> seekingPosts = postService.listAllSeekingPosts(currentUser);
        model.addAttribute("posts", seekingPosts);
        return "users/seeking-posts";
    }

    @GetMapping("/create-seeking-post")
    public String showCreateSeekingPostForm(HttpSession session, Model model)
    {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        return "users/create-seeking-post";
    }

    @GetMapping("/all-posts")
    public String showAllPosts(
            @RequestParam(value = "postType", required = false) String postType,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "courseId", required = false) Long courseId,
            HttpSession session,
            Model model)
    {
        postType = postType == null ? "ALL" : postType;

        List<Post> posts = postService.filterPosts(postType, categoryId, courseId);

        //dropdown
        model.addAttribute("posts", posts);
        model.addAttribute("postTypes", Arrays.asList("ALL", "SELL", "FREE", "SEEKING"));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("courses", courseService.getAllCourses());

        model.addAttribute("postType", postType);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedCourseId", courseId);

        return "users/all-posts";
    }

    @PostMapping("/create-free-posts")
    public String createFreePost(
            @ModelAttribute("post") Post post,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session)
    {

        post.setPrice((short)0);
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();
        var createdPost = postService.createFreePost(post, currentUser, file);
        return "redirect:/users/free-posts";
    }

    @PostMapping("/create-sell-post")
    public String createSellPost(
            @ModelAttribute("post") Post post,
            @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session)
    {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();
        var createdPost = postService.createSellPost(post, currentUser, file);
        return "redirect:/users/sell-posts";
    }

    @PostMapping("/create-seeking-post")
    public String createSeekingPost(
            @ModelAttribute("post") Post post,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session)
    {

        post.setPrice((short) 0);
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();
        var createdPost = postService.createSeekingPost(post, currentUser, file);
        return "redirect:/users/seeking-posts";
    }
}


