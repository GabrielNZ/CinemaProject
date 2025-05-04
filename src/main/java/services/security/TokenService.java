package services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwtauth}")
    private String jwtalgorithm;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtalgorithm);
            return JWT.create().withIssuer("auth_api").withSubject(user.getUsername()).withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC)).sign(algorithm);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while creation token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtalgorithm);
            return JWT.require(algorithm).withIssuer("auth_api").build().verify(token).getSubject();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while validating token");
        }
    }
}
