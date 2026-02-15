package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.request.CreateDomainRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.entity.Domain;
import com.hellFire.QuizGame.services.impl.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domains")
public class DomainController {

    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Domain>> create(
            @RequestBody CreateDomainRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(domainService.createDomain(request),
                        "Domain created successfully")
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Domain>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(domainService.getAllDomains())
        );
    }
}
