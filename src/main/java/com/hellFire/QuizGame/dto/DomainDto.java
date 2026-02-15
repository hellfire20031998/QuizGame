package com.hellFire.QuizGame.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DomainDto extends BaseEntityDto{
    private String name;
}
