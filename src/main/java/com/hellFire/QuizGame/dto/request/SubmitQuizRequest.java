package com.hellFire.QuizGame.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubmitQuizRequest {

    private Long quizId;
    private Long totalTimeInSeconds;
    private List<SubmitAnswerDto> answers;
}