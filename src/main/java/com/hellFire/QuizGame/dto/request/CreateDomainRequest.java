package com.hellFire.QuizGame.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDomainRequest {
    @NotBlank(message = "Domain name is required")
    private String name;
}
