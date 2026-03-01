package com.hellFire.QuizGame.mapper;

import com.hellFire.QuizGame.dto.AnswerSubmissionDto;
import com.hellFire.QuizGame.dto.QuizSubmissionDto;
import com.hellFire.QuizGame.entity.AnswerSubmission;
import com.hellFire.QuizGame.entity.QuizSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IQuizSubmissionMapper {
    QuizSubmissionDto toDto(QuizSubmission submission);

    AnswerSubmissionDto toDto(AnswerSubmission answer);
}
