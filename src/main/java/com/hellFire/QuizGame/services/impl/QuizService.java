package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.mapper.IQuizMapper;
import com.hellFire.QuizGame.repositories.IQuizRepository;
import com.hellFire.QuizGame.services.IQuestionService;
import com.hellFire.QuizGame.services.IQuizService;
import com.hellFire.QuizGame.services.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService implements IQuizService {

    private final IQuizRepository quizRepository;
    private final IQuizMapper quizMapper;
    private final IUserService userService;
    private final IQuestionService questionService;

    public QuizService(IQuizRepository quizRepository, IQuizMapper quizMapper, IUserService userService, IQuestionService questionService) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Transactional
    public QuizDto createQuiz(CreateQuizRequest request, String email){
        User user = userService.getUserByEmail(email);
        Quiz quiz = quizMapper.createEntity(request);
        quiz.setCreatedBy(user);
        List<Question> questionList = questionService.createEntity(request.getQuestionRequests());
        quiz.setQuestions(questionList);
        quiz = quizRepository.save(quiz);
        return toDto(quiz);
    }


    public QuizDto toDto(Quiz quiz){
        return quizMapper.toDto(quiz);
    }

    public List<QuizDto> toDtoList(List<Quiz> quizList){
        return quizMapper.toDtoList(quizList);
    }
}
