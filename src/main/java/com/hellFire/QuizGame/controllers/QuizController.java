package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.request.UpdateQuizRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.dto.response.PaginatedResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IQuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> updateQuiz(
            @PathVariable Long id,
            @RequestBody UpdateQuizRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                ApiResponse.success(quizService.updateQuiz(id, request, user), "Quiz updated successfully")
        );
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<String>> publishQuiz(@PathVariable Long id) {
        quizService.publishQuiz(id);
        return ResponseEntity.ok(ApiResponse.success("Quiz published"));
    }

    @PatchMapping("/{id}/unpublish")
    public ResponseEntity<ApiResponse<String>> unpublishQuiz(@PathVariable Long id) {
        quizService.unpublishQuiz(id);
        return ResponseEntity.ok(ApiResponse.success("Quiz unpublished"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<PaginatedResponse<QuizDto>>> getAllQuiz(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(ApiResponse.success(quizService.getAllQuiz(page, size)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GAME_MASTER')")
    public ResponseEntity<ApiResponse<String>> deleteQuiz(
            @PathVariable Long id,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        quizService.deleteQuiz(id, user);

        return ResponseEntity.ok(ApiResponse.success("Quiz deleted successfully"));
    }

    @GetMapping("/get-all-my")
    @PreAuthorize("hasRole('GAME_MASTER')")
    public ResponseEntity<ApiResponse<PaginatedResponse<QuizDto>>> getAllMyQuiz(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(quizService.getAllMyQuiz(page, size, user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> getQuizById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(quizService.getMyQuizById(id, user)));
    }


}
