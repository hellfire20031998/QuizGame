package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    boolean existsByQuizIdAndCandidateId(Long quizId, Long candidateId);

    List<QuizSubmission> findByQuizIdAndCandidateId(Long quizId, Long candidateId);
}
