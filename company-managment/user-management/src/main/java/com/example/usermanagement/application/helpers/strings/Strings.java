package com.example.usermanagement.application.helpers.strings;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Strings {
    public static boolean validatePassword(String password){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }
}
