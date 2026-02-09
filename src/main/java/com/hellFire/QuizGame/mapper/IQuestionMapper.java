package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.QuestionDto;
import com.hellFire.QuizGame.dto.request.CreateQuestionRequest;
import com.hellFire.QuizGame.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IQuestionMapper {

    QuestionDto toDto(Question question);
    List<QuestionDto> toDtoList(List<Question> questionList);
    List<Question> createQuestionList(List<CreateQuestionRequest> createQuestionRequests);
    Question createQuestion(CreateQuestionRequest request);
}
