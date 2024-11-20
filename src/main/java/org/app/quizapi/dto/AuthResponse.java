package org.app.quizapi.dto;


import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
   private String token;
   private String username;
}
