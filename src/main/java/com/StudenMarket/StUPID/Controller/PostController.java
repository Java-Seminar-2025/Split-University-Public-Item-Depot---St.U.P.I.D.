package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.Category;
import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Exception.AppException;
import com.StudenMarket.StUPID.Service.CategoryService;
import com.StudenMarket.StUPID.Service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/sell-posts")
    public String showSellPosts(HttpSession session, Model model) {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<Post> sellPosts = postService.listAllSellPosts(currentUser);
        model.addAttribute("posts", sellPosts);
        return "users/sell-posts";
    }

    @GetMapping("/create-sell-post")
    public String showCreateSellPostForm(HttpSession session, Model model) {
        User currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<Category> categories = categoryService.findAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        return "users/create-sell-post";
    }

    @PostMapping("/create-sell-post")
    public String createSellPost(
            @ModelAttribute("post") Post post,
            @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session)
    {
        User currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();
        Post createdPost = postService.createSellPost(post, currentUser, file);
        return "redirect:/users/sell-posts";
    }
}


