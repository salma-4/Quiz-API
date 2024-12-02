package org.app.quizapi.security;

import lombok.RequiredArgsConstructor;
import org.app.quizapi.entity.User;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getByUsername(username)
                .orElseThrow(()-> new RecordNotFoundException("User not found "));
        return user;
    }
}
