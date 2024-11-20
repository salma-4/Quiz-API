package org.app.quizapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    private  final JWTService jwtService;
    private  final UserDetailsService userDetailsService;
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
        log.error("403 NO token sent ");
        return;
    }

    final String jwt = header.substring(7);
    //2-got to userDetailsService to check if the user exist in db or not
    String username = jwtService.extractUserName(jwt);
    if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
        // extract user data
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
