package com.hellFire.QuizGame.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuizSubmissionDto extends BaseEntityDto {

    private QuizDto quiz;
    private UserDto candidate;

    private Integer score;
    private boolean completed;

    private List<AnswerSubmissionDto> answers;
}