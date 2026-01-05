package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.Post;
import com.StudenMarket.StUPID.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByAuthorAndPriceGreaterThan(User author, short price);

    Optional<List<Post>> findByAuthorAndPriceEqualsAndSeekingFalse(User author, short price);

    Optional<List<Post>> findByAuthorAndSeeking(User author, boolean seeking);

    Optional<Post> findById(Long id);

    List<Post> findByPriceGreaterThan(short price);

    @Query("SELECT p FROM Post p " +
                "WHERE (:postType IS NULL OR " +
                "       (:postType = 'SELL' AND p.price > 0) OR " +
                "       (:postType = 'FREE' AND p.price = 0 AND p.seeking = false) OR " +
                "       (:postType = 'SEEKING' AND p.seeking = true)) " +
                "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
                "AND (:courseId IS NULL OR p.author.course.Id = :courseId)")
    List<Post> filterPosts(
                @Param("postType") String postType,
                @Param("categoryId") Long categoryId,
                @Param("courseId") Long courseId
    );
}
