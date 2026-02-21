package com.hellFire.QuizGame.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuizRequest {
    private String title;
    private String description;
    private List<UpdateQuestionRequest> questionRequests;
    private List<Long> domains;
}