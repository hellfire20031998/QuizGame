package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.entity.Quiz;

import java.util.List;

public interface IQuizService {
    QuizDto toDto(Quiz quiz);
    List<QuizDto> toDtoList(List<Quiz> quizList);
}

