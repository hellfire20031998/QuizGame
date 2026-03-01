package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.request.UpdateQuizRequest;
import com.hellFire.QuizGame.dto.response.ApiResponse;
import com.hellFire.QuizGame.dto.response.PaginatedResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.IQuizService;
import com.hellFire.QuizGame.services.impl.QuizSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private final IQuizService quizService;
    private final QuizSearchService quizSearchService;

    public QuizController(IQuizService quizService, QuizSearchService quizSearchService) {
        this.quizService = quizService;
        this.quizSearchService = quizSearchService;
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

    @PatchMapping("/{id}/toggle-publish")
    public ResponseEntity<ApiResponse<String>> togglePublish(@PathVariable Long id,
                                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean newStatus = quizService.togglePublishStatus(id, user);

        String message = newStatus ? "Quiz published" : "Quiz unpublished";
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<PaginatedResponse<QuizDto>>> getAllQuiz(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(quizService.getAllQuiz(page, size, user)));
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

    @GetMapping("/my/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> getMyQuizById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(quizService.getMyQuizById(id, user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizDto>> getQuizById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(quizService.getQuizById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> search(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (term == null || term.trim().length() < 3) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("SEARCH_TERM_TOO_SHORT",
                            "Search term must be at least 3 characters long"));
        }

        return ResponseEntity.ok(ApiResponse.success(
                quizSearchService.search(term.trim(), page, size)
        ));
    }
}
