package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.request.UpdateQuizRequest;
import com.hellFire.QuizGame.dto.response.PaginatedResponse;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuizService {
    PaginatedResponse<QuizDto> getAllQuiz(int page, int size, User user);
    PaginatedResponse<QuizDto> getAllMyQuiz(int page, int size, User user);
    QuizDto getMyQuizById(Long id, User user);
    QuizDto getQuizById(Long id);
    QuizDto toDto(Quiz quiz);
    boolean togglePublishStatus(Long id, User user);
    void deleteQuiz(Long id, User user);
    List<QuizDto> toDtoList(List<Quiz> quizList);
    QuizDto createQuiz(CreateQuizRequest request, User user);
    QuizDto updateQuiz(Long id, UpdateQuizRequest request, User user);
}

