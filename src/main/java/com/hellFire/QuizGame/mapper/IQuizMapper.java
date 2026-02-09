package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.request.CreateQuizRequest;
import com.hellFire.QuizGame.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IQuizMapper {
    QuizDto toDto(Quiz quiz);
    List<QuizDto> toDtoList(List<Quiz> quizList);
    Quiz createEntity(CreateQuizRequest request);
}
