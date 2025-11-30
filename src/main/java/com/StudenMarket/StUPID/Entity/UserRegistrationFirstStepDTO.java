package com.StudenMarket.StUPID.Entity;

import lombok.Data;

@Data
public class UserRegistrationFirstStepDTO {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private int age;
    private int phoneNumber;
    private Gender gender;
    private Long universityId;
}