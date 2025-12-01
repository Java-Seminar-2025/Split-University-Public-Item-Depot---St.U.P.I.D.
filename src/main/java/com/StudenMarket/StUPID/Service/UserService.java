package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.Course;
import com.StudenMarket.StUPID.Entity.Role;
import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Entity.UserRegistrationFirstStepDTO;
import com.StudenMarket.StUPID.Exception.AppException;
import com.StudenMarket.StUPID.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Predicate<UserRegistrationFirstStepDTO> validateFirstStep() {
        return firstStepDTO -> {
            validateUsername(firstStepDTO.getUsername());
            validateEmail(firstStepDTO.getEmail());
            validateAge(firstStepDTO.getAge());
            validatePhoneNumber(firstStepDTO.getPhoneNumber());
            return true;
        };
    }

    public Function<UserRegistrationFirstStepDTO, User> prepareUserForSecondStep() {
        return firstStepDTO -> {
            User user = new User();
            user.setName(firstStepDTO.getName());
            user.setLastName(firstStepDTO.getLastName());
            user.setUsername(firstStepDTO.getUsername());
            user.setEmail(firstStepDTO.getEmail());
            user.setAge(firstStepDTO.getAge());
            user.setPhoneNumber(firstStepDTO.getPhoneNumber());
            user.setGender(firstStepDTO.getGender());
            user.setRole(Role.STUDENT);
            return user;
        };
    }

    public BiFunction<User, Course, User> completeUserRegistration() {
        return (user, course) -> {
            user.setCourse(course);
            return userRepository.save(user);
        };
    }

    private void validateUsername(String username) {
        Optional.ofNullable(username)
                .filter(u -> !userRepository.existsByUsername(u))
                .orElseThrow(() -> new AppException("Username already exists!"));
    }

    private void validateEmail(String email) {
        Optional.ofNullable(email)
                .filter(e -> e.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
                .filter(e -> !userRepository.existsByEmail(e))
                .orElseThrow(() -> new AppException("Invalid email or email already exists!"));
    }

    private void validateAge(int age) {
        if (age < 16 || age > 40) {
            throw new AppException("Age must be between 16 and 40!");
        }
    }

    private void validatePhoneNumber(int phoneNumber) {
        String phoneStr = String.valueOf(phoneNumber);
        Optional.of(phoneStr)
                .filter(p -> p.length() >= 9 && p.length() <= 15)
                .orElseThrow(() -> new AppException("Phone number must be between 9 and 15 digits!"));
    }

    public User userLogin(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new AppException("Username or Email doesn't exist!"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User fingUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Username doesn't exist!"));
    }
}