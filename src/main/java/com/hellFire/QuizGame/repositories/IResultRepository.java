package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResultRepository extends JpaRepository<Result, Long> {
}
