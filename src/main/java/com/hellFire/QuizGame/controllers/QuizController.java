package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private final IQuizService quizService;

    public QuizController(IQuizService quizService) {
        this.quizService = quizService;
    }

    @PreAuthorize("hasRole('GAME_MASTER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(
            @RequestBody CreateQuizRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponse.success(quizService.createQuiz(request, user), "Quiz created successfully")
        );
    }

}
