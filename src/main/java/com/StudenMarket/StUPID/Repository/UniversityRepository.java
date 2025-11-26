package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.University;
import com.StudenMarket.StUPID.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long>{
}
