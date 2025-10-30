package com.StudenMarket.StUPID.Entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private short price;
    private String status;
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administratorid")
    private User administrator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId")
    private User buyer;

    @OneToMany(mappedBy = "post")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<FavoritePost> favoritePosts = new ArrayList<>();
}