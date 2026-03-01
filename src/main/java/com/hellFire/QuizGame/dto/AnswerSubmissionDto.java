package com.hellFire.QuizGame.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerSubmissionDto extends  BaseEntityDto {

    private QuestionDto question;
    private OptionDto selectedOption;
}