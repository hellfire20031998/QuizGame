package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, Long> {
}
