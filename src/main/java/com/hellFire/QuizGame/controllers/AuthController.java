package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.LoginRequest;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.dto.response.AuthResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IUserService;
import com.hellFire.QuizGame.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequest request) {
        return userService.toDto(userService.createUser(request));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail());

        if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setToken(token);

        return response;
    }
}

