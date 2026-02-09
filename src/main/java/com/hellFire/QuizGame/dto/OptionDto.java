package com.hellFire.QuizGame.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OptionDto extends BaseEntityDto{
    private String text;
    private boolean correct;
}
