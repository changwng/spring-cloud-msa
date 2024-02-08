package com.example.springbootclient.domain.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String login(String userName, String password) {
//        return JwtUtil.createJwtAccessToken(userName);
        return "JwtUtil.createJwtAccessToken(userName)";
    }
}
