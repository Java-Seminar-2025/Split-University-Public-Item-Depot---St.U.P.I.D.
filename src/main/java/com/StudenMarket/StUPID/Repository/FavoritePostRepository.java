package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.FavoritePost;
import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
    List<FavoritePost> findByUserIdOrderByDateDesc(User user);
    boolean existsByUserIdAndPostId(User user, Post post);
    Optional<FavoritePost> findByUserIdAndPostId(User user, Post post);
}