package com.StudenMarket.StUPID.Controller;

import com.StudenMarket.StUPID.Entity.FavoritePost;
import com.StudenMarket.StUPID.Service.FavoritePostService;
import com.StudenMarket.StUPID.Service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/users")
public class FavoritePostController {
    @Autowired
    private FavoritePostService favoritePostService;
    @Autowired
    private PostService postService;

    @GetMapping("/favorite-posts")
    public String showFavoritePosts(HttpSession session, Model model)
    {
        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();

        List<FavoritePost> favoritePosts = favoritePostService.getUserFavoritePosts(currentUser);
        model.addAttribute("favoritePosts", favoritePosts);
        return "users/favorite-posts";
    }

    @PostMapping("/add-to-favorites")
    public String toggleFavorites(
            @RequestParam Long postId,
            HttpSession session,
            @RequestHeader(value = "Referer", required = false) String referrer)
    {

        var currentUser = HelpMetods.validateLoggedInUser(session).orElseThrow();
        var post = postService.findPostById(postId);

        favoritePostService.toggleFavoritePost(currentUser, post);
        return "redirect:" + (referrer != null ? referrer : "/users/sell-posts");
    }
}