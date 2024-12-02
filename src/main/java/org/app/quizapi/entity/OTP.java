package org.app.quizapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String otp;
    private LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
