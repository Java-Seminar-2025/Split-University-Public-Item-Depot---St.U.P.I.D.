package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Repository.UserRepository;
import com.StudenMarket.StUPID.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{6,}$";

    private void validateEmail(String email) {
        Optional.ofNullable(email)
                .filter(e -> e.matches(EMAIL_REGEX))
                .orElseThrow(() -> new AppException("Invalid email format"));

        if (userRepository.existsByEmail(email)) {
            throw new AppException("Email already exists!");
        }
    }

    private void validateUsername(String username) {
        Optional.ofNullable(username)
                .filter(u -> !userRepository.existsByUsername(u))  // ← Ovo treba vratiti TRUE da prođe
                .orElseThrow(() -> new AppException("Username already exists!"));
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


    private void validatePassword(String password) {
        Optional.ofNullable(password)
                .filter(p -> p.matches(PASSWORD_REGEX))
                .orElseThrow(() -> new AppException(
                        "Password must contain at least one uppercase letter, " +
                                "at least one number, and at least 6 characters!"
                ));
    }

    public User userRegister(User user) {
        validateEmail(user.getEmail());
        validateUsername(user.getUsername());
        validateAge(user.getAge());
        validatePassword(user.getPassword());
        validatePhoneNumber(user.getPhoneNumber());

        return userRepository.save(user);
    }

    public User userLogin(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new AppException("Username or Email doesn't exist!"));
    }
}