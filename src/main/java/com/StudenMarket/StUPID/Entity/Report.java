package com.StudenMarket.StUPID.Entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reports")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date created;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSender")
    private User reportsenderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedUser")
    private User ReportedUser;
}
