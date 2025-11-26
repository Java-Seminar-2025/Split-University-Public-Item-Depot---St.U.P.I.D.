package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.Course;
import com.StudenMarket.StUPID.Entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>
{
    public List<Course> findByUniversity(University university);
}
