package com.hellFire.QuizGame.services;

import com.hellFire.QuizGame.dto.OptionDto;
import com.hellFire.QuizGame.dto.request.CreateOptionRequest;
import com.hellFire.QuizGame.entity.Option;
import com.hellFire.QuizGame.entity.Question;

import java.util.List;

public interface IOptionService {
    OptionDto toDto(Option option);
    List<OptionDto> toDtoList(List<Option> options);
    List<Option> createEntities(List<CreateOptionRequest> optionRequests, Question question);
}
