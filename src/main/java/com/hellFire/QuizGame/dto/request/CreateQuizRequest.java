package com.hellFire.QuizGame.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuizRequest {
    private String title;
    private String description;
    private List<CreateQuestionRequest> questionRequests;
    private List<Long> domains;
}
