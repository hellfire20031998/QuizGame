package com.hellFire.QuizGame.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Quiz quiz;

    private int score;

    private LocalDateTime completedAt;
}
