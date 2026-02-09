package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.OptionDto;
import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.request.CreateQuestionRequest;
import com.hellFire.QuizGame.entity.Option;
import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.mapper.IOptionMapper;
import com.hellFire.QuizGame.mapper.IQuestionMapper;
import com.hellFire.QuizGame.repositories.IQuestionRepository;
import com.hellFire.QuizGame.services.IOptionService;
import com.hellFire.QuizGame.services.IQuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    private final IQuestionRepository questionRepository;
    private final IQuestionMapper questionMapper;
    private final IOptionService optionService;

    public QuestionService(IQuestionRepository questionRepository, IQuestionMapper questionMapper, IOptionService optionService) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.optionService = optionService;
    }

    public List<Question> createEntity(List<CreateQuestionRequest> requests){
        List<Question> questionList = new ArrayList<>();
        for(CreateQuestionRequest request : requests){
            Question question = questionMapper.createQuestion(request);
            question = questionRepository.save(question);
            List<Option> optionList = optionService.createEntities(request.getOptionRequests(), question);
            question.setOptions(optionList);
            questionList.add(question);
        }
        return questionList;
    }

    public List<QuestionDto> toDtoList(List<Question> questionList){
        return questionMapper.toDtoList(questionList);
    }

    public QuestionDto toDto(Question question){
        return questionMapper.toDto(question);
    }


}
