package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.FavoritePost;
import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Exception.AppException;
import com.StudenMarket.StUPID.Repository.FavoritePostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoritePostService
{
    @Autowired
    private FavoritePostRepository favoritePostRepository;

    public boolean isPostInFavorites(User user, Post post) {
        return favoritePostRepository.existsByUserIdAndPostId(user, post);
    }

    @Transactional
    public void toggleFavoritePost(User user, Post post)
    {
        if (isPostInFavorites(user, post)) {
            removeFromFavorites(user, post);
        } else {
            addToFavorites(user, post);
        }
    }

    @Transactional
    public FavoritePost addToFavorites(User user, Post post)
    {
        if (favoritePostRepository.existsByUserIdAndPostId(user, post)) {
            throw new AppException("Post is already in favorites");
        }

        var favoritePost = new FavoritePost();
        favoritePost.setUserId(user);
        favoritePost.setPostId(post);
        favoritePost.setDate(new Date());

        return favoritePostRepository.save(favoritePost);
    }

    public List<FavoritePost> getUserFavoritePosts(User user)
    {
        return favoritePostRepository.findByUserIdOrderByDateDesc(user);
    }

    @Transactional
    public void removeFromFavorites(User user, Post post)
    {
        favoritePostRepository.findByUserIdAndPostId(user, post)
                .ifPresent(favoritePostRepository::delete);
    }
}