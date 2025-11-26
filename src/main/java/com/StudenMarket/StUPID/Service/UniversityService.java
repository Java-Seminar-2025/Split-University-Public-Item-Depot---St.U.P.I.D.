package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.University;
import com.StudenMarket.StUPID.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
    @Autowired
    private UniversityRepository universityRepository;

    public List<University> listAllUniversity() {
        return universityRepository.findAll();
    }
}

