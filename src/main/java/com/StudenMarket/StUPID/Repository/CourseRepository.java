package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>
{
    List<Course> findByUniversity_Id(Long universityId);
}
