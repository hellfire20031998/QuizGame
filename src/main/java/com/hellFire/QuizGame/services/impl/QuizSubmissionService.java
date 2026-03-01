package com.hellFire.QuizGame.services.impl;

import com.hellFire.QuizGame.dto.QuizSubmissionDto;
import com.hellFire.QuizGame.dto.request.SubmitAnswerDto;
import com.hellFire.QuizGame.dto.request.SubmitQuizRequest;
import com.hellFire.QuizGame.entity.*;
import com.hellFire.QuizGame.mapper.IQuizSubmissionMapper;
import com.hellFire.QuizGame.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuizSubmissionService {

    private final IQuizRepository quizRepository;
    private final IQuestionRepository questionRepository;
    private final IOptionRepository optionRepository;
    private final IQuizSubmissionRepository submissionRepository;
    private final IQuizSubmissionMapper mapper;

    public QuizSubmissionService(IQuizRepository quizRepository, IQuestionRepository questionRepository, IOptionRepository optionRepository, IQuizSubmissionRepository submissionRepository, IQuizSubmissionMapper mapper) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.submissionRepository = submissionRepository;
        this.mapper = mapper;
    }

    @Transactional
    public QuizSubmissionDto submitQuiz(SubmitQuizRequest request, User candidate) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if(Objects.equals(quiz.getCreatedBy(), candidate)){
            throw  new RuntimeException("Creators can not submit for their quiz");
        }
        boolean exists = submissionRepository
                .existsByQuizIdAndCandidateId(request.getQuizId(), candidate.getId());

        if (exists) {
            throw new RuntimeException("You have already submitted this quiz.");
        }

        QuizSubmission submission = new QuizSubmission();
        submission.setQuiz(quiz);
        submission.setCandidate(candidate);
        submission.setTotalTimeInSeconds(request.getTotalTimeInSeconds());

        int score = 0;
        List<AnswerSubmission> answerList = new ArrayList<>();

        for (SubmitAnswerDto ans : request.getAnswers()) {

            Question question = questionRepository.findById(ans.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            Option option = optionRepository.findById(ans.getSelectedOptionId())
                    .orElseThrow(() -> new RuntimeException("Option not found"));

            AnswerSubmission answerSubmission = new AnswerSubmission();
            answerSubmission.setSubmission(submission);
            answerSubmission.setQuestion(question);
            answerSubmission.setSelectedOption(option);

            if (option.isCorrect()) {
                score++;
            }

            answerList.add(answerSubmission);
        }

        submission.setAnswers(answerList);
        submission.setScore(score);
        submission.setCompleted(true);

        submissionRepository.save(submission);

        return mapper.toDto(submission);
    }
}