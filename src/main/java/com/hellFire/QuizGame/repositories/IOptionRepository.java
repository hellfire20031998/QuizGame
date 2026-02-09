package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOptionRepository extends JpaRepository<Option, Long> {
}
