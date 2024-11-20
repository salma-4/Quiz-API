package org.app.quizapi.mapper;

import org.app.quizapi.dto.UserRegisterDTO;
import org.app.quizapi.entity.User;

public interface UserMapper {

    User toRegisterEntity(UserRegisterDTO user);

}
