package org.app.quizapi.repository;

import org.app.quizapi.entity.OTP;
import org.app.quizapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepo extends JpaRepository<OTP,Long> {
    Optional<OTP> findByOtpAndUser(String otp, User user);
}
