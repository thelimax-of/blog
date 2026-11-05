package br.com.limax.blog.auth.infrastructure.security;

import br.com.limax.blog.auth.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            var algorith = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withIssuer("blog-pessoal")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorith);

            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token.", exception);
        }
    }

    public String validateToken(String token){
        try{
            var algorith = Algorithm.HMAC256(secret);
            return JWT.require(algorith)
                    .withIssuer("blog-pessoal")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
