package com.hellFire.QuizGame.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequest {
    private String text;
    private List<CreateOptionRequest> optionRequests;
}
