package com.example.usermanagement.application.helpers.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Crypto {
    @Autowired
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static String encrypt(String password){

        String encrypted = bCryptPasswordEncoder.encode(password);

        return encrypted;
    }

    public static boolean matches(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
