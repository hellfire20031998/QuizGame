package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.QuizDto;
import com.hellFire.QuizGame.dto.response.PaginatedResponse;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.repositories.IQuizDomainMappingRepository;
import com.hellFire.QuizGame.services.IQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizSearchService {

    private final IQuizDomainMappingRepository mappingRepository;
    private final IQuizService quizService;

    public PaginatedResponse<QuizDto> search(String term, int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "quiz.createdAt")
        );

        Page<Quiz> quizPage = mappingRepository.searchByTitleOrDomain(term, pageable);

        List<QuizDto> quizDtos = quizPage.getContent().stream()
                .map(quizService::toDto)
                .toList();

        return new PaginatedResponse<>(
                quizDtos,
                quizPage.getNumber(),
                quizPage.getSize(),
                quizPage.getTotalElements(),
                quizPage.getTotalPages(),
                quizPage.isLast()
        );
    }
}