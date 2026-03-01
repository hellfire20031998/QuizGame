package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.Quiz;
import com.hellFire.QuizGame.entity.QuizDomainMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizDomainMappingRepository extends JpaRepository<QuizDomainMapping, Long> {
    @Query("""
    SELECT DISTINCT q 
    FROM QuizDomainMapping qdm
    JOIN qdm.quiz q
    JOIN qdm.domain d
    WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :term, '%'))
       OR LOWER(d.name) LIKE LOWER(CONCAT('%', :term, '%'))
""")
    Page<Quiz> searchByTitleOrDomain(@Param("term") String term, Pageable pageable);
}
