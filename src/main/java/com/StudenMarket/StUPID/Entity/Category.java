package com.StudenMarket.StUPID.Entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorys")
@Getter
@Setter
@ToString(exclude = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();
}
