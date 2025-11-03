package com.StudenMarket.StUPID.Service;

import com.StudenMarket.StUPID.Entity.User;
import com.StudenMarket.StUPID.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.lang.RuntimeException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{6,}$";

        return password.matches(passwordRegex);
    }


    public User registerUser(User user){

        if(!isValidEmail(user.getEmail())){
            throw new RuntimeException("Invalid email");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email Already Exists!");
        }
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username Already Exists!");
        }
        if(user.getAge() < 16 || user.getAge() > 40){
            throw new RuntimeException("Age must be between 16 and 40!");
        }
        if(user.getPassword() == null || !isValidPassword(user.getPassword())){
            throw new RuntimeException("Password must contain at least one uppercase letter," +
                    "must contain at least one number, and must contain at least 6 characters!");
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
        return user;
    }

    public User userLogin(User user){
        if(userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()) == null){
            throw new RuntimeException("Username or Email Doesn't Exist!");
        }
        return user;
    }


}
