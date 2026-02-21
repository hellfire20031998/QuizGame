package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.request.CreateQuestionRequest;
import com.hellFire.QuizGame.dto.request.UpdateQuestionRequest;
import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.entity.Quiz;

import java.util.List;

public interface IQuestionService {
    QuestionDto toDto(Question question);
    List<QuestionDto> toDtoList(List<Question> questionList);
    List<Question> createEntity(List<CreateQuestionRequest> questionRequests);
    void deleteByQuiz(Quiz quiz);
    List<Question> updateEntity(List<UpdateQuestionRequest>requests);
}
