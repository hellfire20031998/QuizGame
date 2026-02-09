package com.hellFire.QuizGame.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option extends BaseEntity {

    private String text;
    private boolean correct;

    @ManyToOne
    private Question question;
}

