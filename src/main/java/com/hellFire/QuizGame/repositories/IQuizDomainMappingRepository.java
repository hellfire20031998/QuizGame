package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.QuizDomainMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizDomainMappingRepository extends JpaRepository<QuizDomainMapping, Long> {
}
