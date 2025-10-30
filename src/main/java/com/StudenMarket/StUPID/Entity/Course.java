package com.StudenMarket.StUPID.Entity;

import java.util.*; // za dete, arrays, list
import jakarta.persistence.*; //za entity, table, relacije
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId")
    private University university;
}
