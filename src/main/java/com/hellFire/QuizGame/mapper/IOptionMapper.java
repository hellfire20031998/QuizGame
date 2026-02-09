package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.OptionDto;
import com.hellFire.QuizGame.dto.request.CreateOptionRequest;
import com.hellFire.QuizGame.entity.Option;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IOptionMapper {

    OptionDto toDto(Option option);
    List<OptionDto> toDtoList(List<Option> options);
    List<Option> createEntity(List<CreateOptionRequest> optionRequests);
}
