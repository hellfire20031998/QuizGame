package com.hellFire.QuizGame.dto.request;

import lombok.Data;

@Data
public class UpdateOptionRequest {
    private Long id;
    private String text;
    private boolean correct;
}
