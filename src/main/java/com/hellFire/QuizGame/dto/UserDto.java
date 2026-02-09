package com.hellFire.QuizGame.dto;

import com.hellFire.QuizGame.entity.enums.Role;

public class UserDto extends BaseEntityDto{
    private String username;
    private String email;
    private String name;
    private Role role;
}
