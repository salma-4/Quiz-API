package org.app.quizapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.OTP;
import org.app.quizapi.entity.User;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.otp.OTPMapper;
import org.app.quizapi.repository.OTPRepo;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.service.OTPService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
    private  final OTPMapper otpMapper;
    private final OTPRepo otpRepo;
    private final UserRepo userRepo;

    private static int OTP_LENGTH=6;

    @Override
    public OTPResponseDTO generateOTP(User user) {
     String otpCode = generateNumericOtp(OTP_LENGTH);
        OTP otp = OTP.builder()
                .otp(otpCode)
                .expirationTime(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        otpRepo.save(otp);

        return otpMapper.toDTO(otp);
    }

    @Override
    public boolean validateOtp(String otpCode ,User user) {
        OTP existingOTP = otpRepo.findByOtpAndUser(otpCode,user)
              .orElseThrow(()->new RecordNotFoundException("Check OTP "));

        if(existingOTP.getExpirationTime().isBefore(LocalDateTime.now()))
            return false;

        otpRepo.delete(existingOTP);// TODO find another way ;)

        return true;
    }

    private String generateNumericOtp(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
