package org.app.quizapi.dto.otp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTPRequestDTO {
    private String otp;
    private String email;
    private String newPassword;
}
