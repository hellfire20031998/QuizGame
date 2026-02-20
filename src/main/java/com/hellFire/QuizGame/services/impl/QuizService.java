package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.entity.Domain;
import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.mapper.IQuizMapper;
import com.hellFire.QuizGame.repositories.IQuizRepository;
import com.hellFire.QuizGame.services.IQuestionService;
import com.hellFire.QuizGame.services.IQuizService;
import com.hellFire.QuizGame.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService implements IQuizService {

    private final IQuizRepository quizRepository;
    private final IQuizMapper quizMapper;
    private final IUserService userService;
    private final IQuestionService questionService;
    private final DomainService domainService;
    private final QuizDomainMappingService quizDomainMappingService;

    public QuizService(IQuizRepository quizRepository, IQuizMapper quizMapper, IUserService userService, IQuestionService questionService, DomainService domainService, QuizDomainMappingService quizDomainMappingService) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.userService = userService;
        this.questionService = questionService;
        this.domainService = domainService;
        this.quizDomainMappingService = quizDomainMappingService;
    }

    @Transactional
    public QuizDto createQuiz(CreateQuizRequest request, User user){

        Quiz quiz = quizMapper.createEntity(request);
        quiz.setCreatedBy(user);
        List<Question> questionList = questionService.createEntity(request.getQuestionRequests());
        quiz.setQuestions(questionList);
        quiz = quizRepository.save(quiz);

        List<Domain> domainList = domainService.getDomainsByIds(request.getDomains());
        quizDomainMappingService.createMapping(quiz, domainList);
        return toDto(quiz);
    }

    @Override
    public Page<QuizDto> getAllQuiz(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "createdAt")
        );

        Page<Quiz> quizPage = quizRepository.findAll(pageable);

        return quizPage.map(this::toDto);
    }


    public QuizDto toDto(Quiz quiz){
        return quizMapper.toDto(quiz);
    }

    public List<QuizDto> toDtoList(List<Quiz> quizList){
        return quizMapper.toDtoList(quizList);
    }
}
