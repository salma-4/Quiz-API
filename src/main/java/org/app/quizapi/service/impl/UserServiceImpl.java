package org.app.quizapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.otp.OTPRequestDTO;
import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.otp.OTPMapper;
import org.app.quizapi.mapper.user.UserMapper;
import org.app.quizapi.repository.UserRepo;
import org.app.quizapi.service.OTPService;
import org.app.quizapi.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final OTPService otpService;
    private final OTPMapper otpMapper;
    private final EmailServiceImpl emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public String forgetPassword(String email) {
        User user = userRepo.getByEmail(email)
                .orElseThrow(()->new RecordNotFoundException(email +" not exist"));
       OTPResponseDTO otp = otpService.generateOTP(user);
        emailService.sendOtpEmail(email,otp.getOtp());
        return "Check your mail for verification code";

    }

    @Override
    public String resetPassword(OTPRequestDTO otpRequestDTO) {
        User user = userRepo.getByEmail(otpRequestDTO.getEmail())
                .orElseThrow(()->new RecordNotFoundException(otpRequestDTO.getEmail()+" does not exist"));
        if(otpService.validateOtp(otpRequestDTO.getOtp(),user)){
            user.setPassword(bCryptPasswordEncoder.encode(otpRequestDTO.getNewPassword()));
            userRepo.save(user);
            return "Password changed ";
        }
            return "something went wrong try again";
    }

    @Override
    public String regenerateOtp(String email) {
        User user = userRepo.getByEmail(email)
                .orElseThrow(()->new RecordNotFoundException(email +"not exist "));
        OTPResponseDTO otp = otpService.generateOTP(user);
        emailService.sendOtpEmail(email,otp.getOtp());
        return "Check your mail for verification code";
    }
}
