package com.hellFire.QuizGame.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuizDto extends BaseEntityDto{
    private String title;
    private String description;
    private boolean published = false;

    private UserDto createdBy;
    private Long totalTimeInSeconds;

    private List<QuestionDto> questions;
}
