package org.app.quizapi.repository;

import org.app.quizapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);

}
