package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.mapper.IUserMapper;
import com.hellFire.QuizGame.repositories.IUserRepository;
import com.hellFire.QuizGame.services.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public UserService(IUserRepository userRepository, IUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = userMapper.createEntity(request);

        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmailIgnoreCaseAndDeleted(email, false);
    }

    public UserDto toDto(User user){
        return userMapper.toDto(user);
    }

}
