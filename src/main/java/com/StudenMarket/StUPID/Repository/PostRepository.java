package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByAuthorAndPriceGreaterThan(User author, short price);
    Optional<List<Post>> findByAuthorAndPriceEqualsAndSeekingFalse(User author, short price);
    Optional<List<Post>> findByAuthorAndSeeking(User author, boolean seeking);
}
