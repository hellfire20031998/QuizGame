package com.hellFire.QuizGame.dto;

import com.hellFire.QuizGame.entity.Quiz;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionDto extends BaseEntityDto{
    private String text;
    private List<OptionDto> options;
}
