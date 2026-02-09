package com.hellFire.QuizGame.dto;

import java.time.LocalDateTime;

public class BaseEntityDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long version;

    private boolean deleted;
}
