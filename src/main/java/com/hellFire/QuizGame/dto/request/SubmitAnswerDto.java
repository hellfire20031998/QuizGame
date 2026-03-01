package com.hellFire.QuizGame.dto.request;

import lombok.Data;

@Data
public class SubmitAnswerDto {

    private Long questionId;
    private Long selectedOptionId;
}