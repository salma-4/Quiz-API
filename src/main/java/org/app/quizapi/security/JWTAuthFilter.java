package org.app.quizapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.quizapi.entity.UserToken;
import org.app.quizapi.repository.UserTokenRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
public class JWTAuthFilter extends OncePerRequestFilter {

    private  final JWTService jwtService;
    private  final UserDetailsService userDetailsService;
    private final UserTokenRepo userTokenRepo;
    @Override
    protected void doFilterInternal(
             @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain) throws ServletException, IOException {
    try{
    //1- token exist
    final String header = request.getHeader("Authorization");
    if (header== null || ! header.startsWith("Bearer")){
        filterChain.doFilter(request,response);
         return;
    }

    final String jwt = header.substring(7);
    //2-got to userDetailsService to check if the user exist in db or not
    String username = jwtService.extractUserName(jwt);
    if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
        Optional<UserToken> userTokenOptional = userTokenRepo.findByToken(jwt);
        if (userTokenOptional.isEmpty()) {
            log.warn("Token not found in database for user '{}'", username);
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        //validate token
        if(jwtService.isTokenValid(jwt,user) ){
            //Update the security context holder
            Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
      }catch (RuntimeException e){
    e.printStackTrace();
}
        filterChain.doFilter(request, response);
    }
}
