package com.hellFire.QuizGame.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuestionRequest {
    private Long id;
    private String text;
    private List<UpdateOptionRequest> optionRequests;
}
