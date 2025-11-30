package com.StudenMarket.StUPID.Service;


import com.StudenMarket.StUPID.Entity.Course;
import com.StudenMarket.StUPID.Exception.AppException;
import com.StudenMarket.StUPID.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


    @Service
    public class CourseService
    {
        @Autowired
        private CourseRepository courseRepository;

        public List<Course> getCoursesByUniversity(Long universityId) {
            return courseRepository.findByUniversity_Id(universityId);
        }

        public List<Course> getAllCourses() {
            return courseRepository.findAll();
        }

        public Course findById(Long courseId) {
            return courseRepository.findById(courseId)
                    .orElseThrow(() -> new AppException("Course not found with ID: " + courseId));
        }
    }
