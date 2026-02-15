package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.dto.response.AuthResponse;
import com.hellFire.QuizGame.entity.User;

public interface IUserService {
    User createUser(SignUpRequest request);

    User getUserByEmail(String email);

    UserDto toDto(User user);

    AuthResponse toAuthResponse(User user, String token);

    AuthResponse toAuthResponse(User user);
}
