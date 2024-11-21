package org.app.quizapi.dto.user;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
