package com.hellFire.QuizGame.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String email;
    private String username;
}
