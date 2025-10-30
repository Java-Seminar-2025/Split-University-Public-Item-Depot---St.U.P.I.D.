package com.StudenMarket.StUPID.Entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "universitys")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String universityName;

    @OneToMany(mappedBy = "university")
    private List<Course> courses= new ArrayList<>();

}
