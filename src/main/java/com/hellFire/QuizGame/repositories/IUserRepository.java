package com.hellFire.QuizGame.repositories;

import com.hellFire.QuizGame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

    User findByEmail(String email);

}
