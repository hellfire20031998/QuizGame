package com.hellFire.QuizGame.controllers;


import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.dto.response.AuthResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponse>> getCurrentUser(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        AuthResponse response = userService.toAuthResponse(user);

        return ResponseEntity.ok(ApiResponse.success(response));
    }




}
