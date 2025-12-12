package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Exception.AppException;
import com.StudenMarket.StUPID.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.StudenMarket.StUPID.Entity.*;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    private Function<Post, Post> preparePost(User currentUser) {
        return post -> {
            post.setCreated(new Date());
            post.setAuthor(currentUser);
            post.setStatus("PENDING");
            return post;
        };
    }

    private Predicate<Post> validateSellPost()
    {
        return post -> {
            if (post.getPrice() <= 0) {
                throw new AppException("Sell post must have a price greater than zero");
            }
            return true;
        };
    }

    private  Predicate<Post> validateFreePost() {
        return post -> {
            if (post.getPrice() != 0) {
                throw new AppException("Sell post must have a price equals zero");
            }
            return true;
        };
    }

    private Function<MultipartFile, String> uploadImage(User currentUser) {
        return file -> {
            try {
                if (file == null || file.isEmpty()) {
                    return null;
                }

                var fileName = currentUser.getId() + "_" +
                        System.currentTimeMillis() +
                        getFileExtension(file.getOriginalFilename());

                Path uploadPath = Paths.get("./uploads/post-pictures", fileName);
                Files.createDirectories(uploadPath.getParent());
                Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                String imagePath = "/uploads/post-pictures/" + fileName;
                System.out.println("Generated Image Path: " + imagePath);

                return imagePath;
            } catch (IOException e) {
                throw new AppException("Image upload failed");
            }
        };
    }

    public Post createSellPost(Post post, User currentUser, MultipartFile file) {
        return Optional.of(post)
                .filter(validateSellPost())
                .map(preparePost(currentUser))
                .map(p -> {
                    String imagePath = uploadImage(currentUser).apply(file);
                    p.setImageUrl(imagePath);
                    return p;
                })
                .map(postRepository::save)
                .orElseThrow(() -> new AppException("Post creation failed"));
    }


    public List<Post> listAllSellPosts(User user) {
        return postRepository.findByAuthorAndPriceGreaterThan(user, (short) 0)
                .orElse(Collections.emptyList());
    }

    public List<Post> listAllFreePosts(User user) {
        return postRepository.findByAuthorAndPriceEqualsAndSeekingFalse(user, (short) 0)
                .orElse(Collections.emptyList());
    }

    public Post createFreePost(Post post, User currentUser, MultipartFile file) {
        return Optional.of(post)
                .filter(p -> {
                    if (p.getPrice() != 0 || p.isSeeking()) {
                        throw new AppException("Free post must have zero price and not be a seeking post");
                    }
                    return true;
                })
                .map(preparePost(currentUser))
                .map(p -> {
                    p.setSeeking(false);  // Explicit setting
                    String imagePath = uploadImage(currentUser).apply(file);
                    p.setImageUrl(imagePath);
                    return p;
                })
                .map(postRepository::save)
                .orElseThrow(() -> new AppException("Free post creation failed"));
    }

    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".")))
                .orElse(".jpg");
    }

    public List<Post> listAllSeekingPosts(User user) {
        return postRepository.findByAuthorAndSeeking(user, true)
                .orElse(Collections.emptyList());
    }

    public Post createSeekingPost(Post post, User currentUser, MultipartFile file) {
        post.setSeeking(true);
        return Optional.of(post)
                .map(preparePost(currentUser))
                .map(p -> {
                    String imagePath = uploadImage(currentUser).apply(file);
                    p.setImageUrl(imagePath);
                    return p;
                })
                .map(postRepository::save)
                .orElseThrow(() -> new AppException("Seeking post creation failed"));
    }
}

