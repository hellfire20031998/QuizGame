package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Question;
import com.hellFire.QuizGame.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
    void deleteByQuiz(Quiz quiz);
}
