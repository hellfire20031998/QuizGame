package com.hellFire.QuizGame.dto.request;

import com.hellFire.QuizGame.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private String name;
    private String password;
    private Role role;
    private String provider;
}
