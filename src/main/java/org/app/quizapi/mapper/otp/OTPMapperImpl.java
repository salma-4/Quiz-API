package org.app.quizapi.mapper.otp;


import lombok.RequiredArgsConstructor;
import org.app.quizapi.dto.otp.OTPResponseDTO;
import org.app.quizapi.entity.OTP;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OTPMapperImpl implements  OTPMapper {

    @Override
    public OTPResponseDTO toDTO(OTP otp) {
        return OTPResponseDTO.builder()
                .otp(otp.getOtp())
                .email(otp.getUser().getEmail())
                .build();
    }

}
