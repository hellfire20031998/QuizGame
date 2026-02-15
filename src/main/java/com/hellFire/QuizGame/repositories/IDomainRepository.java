package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDomainRepository extends JpaRepository<Domain, Long> {

    Optional<Domain> findByName(String name);

    boolean existsByName(String name);

    List<Domain> findTop10ByOrderByCreatedAtDesc();

    List<Domain> findTop5ByIdNotOrderByCreatedAtDesc(Long id);
}
