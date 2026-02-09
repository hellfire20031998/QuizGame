package com.hellFire.QuizGame.entity;

import com.hellFire.QuizGame.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    private String username;
    private String email;
    private String name;
    private String password;
    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;
}
