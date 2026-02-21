package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.dto.request.UpdateQuizRequest;
import com.hellFire.QuizGame.dto.response.PaginatedResponse;
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
import org.springframework.util.CollectionUtils;

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
        for (Question q : questionList) {
            q.setQuiz(quiz);
        }

        quiz.setQuestions(questionList);

        quiz = quizRepository.save(quiz);

        List<Domain> domainList = domainService.getDomainsByIds(request.getDomains());
        quizDomainMappingService.createMapping(quiz, domainList);
        return toDto(quiz);
    }

    @Transactional
    public QuizDto updateQuiz(Long id, UpdateQuizRequest request, User user) {

        Quiz quiz = quizRepository.findByIdAndCreatedByAndDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quizMapper.updateQuizFromRequest(request, quiz);

        if(CollectionUtils.isEmpty(request.getQuestionRequests())){
            questionService.updateEntity(request.getQuestionRequests());
        }

        return toDto(quiz);
    }

    @Override
    public QuizDto getMyQuizById(Long id, User user) {

        Quiz quiz = quizRepository
                .findByIdAndCreatedByAndDeletedFalse(id, user)
                .orElseThrow(() -> new RuntimeException("Quiz not found or you don't have access"));

        return toDto(quiz);
    }

    @Transactional
    public void deleteQuiz(Long id, User user) {
        Quiz quiz = quizRepository.findByIdAndCreatedBy(id, user)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        quiz.setDeleted(true);
        quizRepository.save(quiz);
    }


    @Override
    public PaginatedResponse<QuizDto> getAllQuiz(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").ascending()
        );

        Page<Quiz> quizPage = quizRepository.findAll(pageable);
        Page<QuizDto> dtoPage = quizPage.map(this::toDto);

        return new PaginatedResponse<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.isLast()
        );
    }

    @Override
    public PaginatedResponse<QuizDto> getAllMyQuiz(int page, int size, User user) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").ascending()
        );

        Page<Quiz> quizPage = quizRepository.findByCreatedByAndDeletedFalse(user, pageable);
        Page<QuizDto> dtoPage = quizPage.map(this::toDto);

        return new PaginatedResponse<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.isLast()
        );
    }

    public void publishQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setPublished(true);
        quizRepository.save(quiz);
    }

    public void unpublishQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setPublished(false);
        quizRepository.save(quiz);
    }

    public QuizDto toDto(Quiz quiz){
        return quizMapper.toDto(quiz);
    }

    public List<QuizDto> toDtoList(List<Quiz> quizList){
        return quizMapper.toDtoList(quizList);
    }
}
