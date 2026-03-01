package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.QuizSubmissionDto;
import com.hellFire.QuizGame.dto.request.SubmitQuizRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.impl.QuizSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submission")
public class QuizSubmissionController {

    private final QuizSubmissionService submissionService;

    public QuizSubmissionController(QuizSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuizSubmissionDto>> submitQuiz(
            @RequestBody SubmitQuizRequest request,
            Authentication authentication) {
        User user = (User)(authentication.getPrincipal());
        QuizSubmissionDto response = submissionService.submitQuiz(request, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}