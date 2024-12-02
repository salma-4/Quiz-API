package org.app.quizapi.mapper.otp;

import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.OTP;

public interface OTPMapper {
    OTPResponseDTO toDTO(OTP otp);
}
