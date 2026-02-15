package com.hellFire.QuizGame.dto.response;

import com.hellFire.QuizGame.entity.enums.Role;
import lombok.Data;

@Data
public class AuthResponse {
    private String email;
    private String username;
    private String token;
    private Role role;
}
