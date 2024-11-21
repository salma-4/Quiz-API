package org.app.quizapi.mapper.user;

import org.app.quizapi.dto.user.UserRegisterDTO;
import org.app.quizapi.entity.User;
import org.app.quizapi.entity.UserToken;

public interface UserMapper {

    User toRegisterEntity(UserRegisterDTO user);
    UserToken toEntityToken(User user,String token);

}
