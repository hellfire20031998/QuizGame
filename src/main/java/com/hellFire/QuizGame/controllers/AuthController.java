package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.LoginRequest;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.dto.response.AuthResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IUserService;
import com.hellFire.QuizGame.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequest request) {
        return userService.toDto(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail());

        if (user == null ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user);

        AuthResponse response = userService.toAuthResponse(user, token);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Login successful")
        );
    }

}

