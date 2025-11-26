package com.StudenMarket.StUPID.Service;


import com.StudenMarket.StUPID.Entity.Course;
import com.StudenMarket.StUPID.Entity.University;
import com.StudenMarket.StUPID.Repository.CourseRepository;
import com.StudenMarket.StUPID.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCoursesByUniversity(University university)
    {
        return courseRepository.findByUniversity(university);
    }
}
