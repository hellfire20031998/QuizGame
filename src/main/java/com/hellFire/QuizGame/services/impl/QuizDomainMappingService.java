package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.entity.Domain;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.QuizDomainMapping;
import com.hellFire.QuizGame.repositories.IQuizDomainMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizDomainMappingService {

    private final IQuizDomainMappingRepository quizDomainMappingRepository;

    public QuizDomainMappingService(IQuizDomainMappingRepository quizDomainMappingRepository) {
        this.quizDomainMappingRepository = quizDomainMappingRepository;
    }

    public void createMapping(Quiz quiz, List<Domain> domainList) {

        if (quiz == null || domainList == null || domainList.isEmpty()) {
            return;
        }

        List<QuizDomainMapping> mappings = domainList.stream()
                .map(domain -> {
                    QuizDomainMapping mapping = new QuizDomainMapping();
                    mapping.setQuiz(quiz);
                    mapping.setDomain(domain);
                    return mapping;
                })
                .toList();

        quizDomainMappingRepository.saveAll(mappings);
    }
}
