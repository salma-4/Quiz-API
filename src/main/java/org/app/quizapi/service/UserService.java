package org.app.quizapi.service;

import org.app.quizapi.dto.otp.OTPRequestDTO;

public interface UserService {
    String forgetPassword(String email);
    String resetPassword( OTPRequestDTO otpRequestDTO);
    String regenerateOtp(String email);
}
