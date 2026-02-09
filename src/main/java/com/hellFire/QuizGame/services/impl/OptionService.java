package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.OptionDto;
import com.hellFire.QuizGame.dto.request.CreateOptionRequest;
import com.hellFire.QuizGame.entity.Option;
import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.mapper.IOptionMapper;
import com.hellFire.QuizGame.repositories.IOptionRepository;
import com.hellFire.QuizGame.services.IOptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService implements IOptionService {

    private final IOptionRepository repository;
    private final IOptionMapper optionMapper;

    public OptionService(IOptionRepository repository, IOptionMapper optionMapper) {
        this.repository = repository;
        this.optionMapper = optionMapper;
    }

    @Override
    public OptionDto toDto(Option option) {
        return optionMapper.toDto(option);
    }

    @Override
    public List<OptionDto> toDtoList(List<Option> options) {
        return optionMapper.toDtoList(options);
    }

    @Override
    public List<Option> createEntities(List<CreateOptionRequest> optionRequests, Question question) {
        List<Option> optionList = optionMapper.createEntity(optionRequests);
        for (Option option : optionList){
            option.setQuestion(question);
        }
        return repository.saveAll(optionList);
    }
}
