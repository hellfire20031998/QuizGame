package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Option;
import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, Long> {
    Page<Quiz> findByCreatedByAndDeletedFalse(User createdBy, Pageable pageable);
    Optional<Quiz> findByIdAndCreatedByAndDeletedFalse(Long id, User createdBy);
    Optional<Quiz> findByIdAndCreatedBy(Long id, User user);
}
