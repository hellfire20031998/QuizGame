package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.DomainDto;
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
    public ResponseEntity<ApiResponse<DomainDto>> create(
            @RequestBody CreateDomainRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(domainService.toDto(domainService.createDomain(request)),
                        "Domain created successfully")
        );
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<DomainDto>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success(domainService.toDtoList(domainService.getAllDomains()))
        );
    }

    @GetMapping("/latest")
    public ApiResponse<List<DomainDto>> getLatestDomains() {
        return ApiResponse.success(
                domainService.toDtoList(domainService.getLatestDomains()),
                "Latest domains fetched successfully"
        );
    }

    @GetMapping("/{id}/related")
    public ApiResponse<List<DomainDto>> getRelatedDomains(@PathVariable Long id) {
        return ApiResponse.success(
                domainService.toDtoList(domainService.getRelatedDomains(id)),
                "Related domains fetched successfully"
        );
    }
}
