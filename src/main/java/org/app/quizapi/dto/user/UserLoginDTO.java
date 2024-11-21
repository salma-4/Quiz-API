package org.app.quizapi.dto.user;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {
    private String username;
    private String password;

}
