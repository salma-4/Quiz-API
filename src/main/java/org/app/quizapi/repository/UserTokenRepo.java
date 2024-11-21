package org.app.quizapi.repository;


import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepo extends JpaRepository<UserToken,Long> {
    Optional<UserToken> findByToken(String jwt);

    Optional<UserToken> findByUser(User existingUser);
}
