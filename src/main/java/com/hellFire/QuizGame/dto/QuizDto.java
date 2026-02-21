package com.hellFire.QuizGame.dto;

import com.hellFire.QuizGame.entity.User;

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

    private List<QuestionDto> questions;
}
