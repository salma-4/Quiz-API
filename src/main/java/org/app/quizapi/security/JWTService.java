package org.app.quizapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.app.quizapi.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {

    // generates a strong, random secret key automatically at runtime

    private final String SECRET_KEY = Base64.getEncoder().encodeToString(
            Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
    );
    private final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 hour

    public String extractUserName(String jwt) {
        Claims claims = extractAllClaims(jwt);
        return claims.getSubject();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUserName(token);
        Claims claims = extractAllClaims(token);

        return (username.equals(user.getUsername()) && !isTokenExpired(claims.getExpiration()));
    }

    private Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractToken(HttpServletRequest request){
        final String header = request.getHeader("Authorization");
        return  header.substring(7);
    }

    private boolean isTokenExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
