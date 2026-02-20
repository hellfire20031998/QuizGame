package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuizService {
    Page<QuizDto> getAllQuiz(int page, int size);

    QuizDto toDto(Quiz quiz);
    List<QuizDto> toDtoList(List<Quiz> quizList);
    QuizDto createQuiz(CreateQuizRequest request, User user);
}

