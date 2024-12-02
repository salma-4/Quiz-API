package org.app.quizapi.service;

import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.User;

public interface OTPService {
    OTPResponseDTO generateOTP(User user);
    boolean validateOtp(String otpCode ,User user);

}
