package com.hellFire.QuizGame.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntityDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long version;

    private boolean deleted;
}
