package com.hellFire.QuizGame.dto.request;

import lombok.Data;

@Data
public class CreateOptionRequest {
    private String text;
    private boolean correct;
}
