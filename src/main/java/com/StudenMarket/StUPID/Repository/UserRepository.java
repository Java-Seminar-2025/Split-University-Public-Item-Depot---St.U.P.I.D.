package com.StudenMarket.StUPID.Repository;

import com.StudenMarket.StUPID.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
}

