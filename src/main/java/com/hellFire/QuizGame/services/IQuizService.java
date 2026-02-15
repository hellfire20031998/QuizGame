package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;

import java.util.List;

public interface IQuizService {
    QuizDto toDto(Quiz quiz);
    List<QuizDto> toDtoList(List<Quiz> quizList);
    QuizDto createQuiz(CreateQuizRequest request, User user);
}

