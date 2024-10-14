package ru.ifmo.insys1.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZonedDateTime;
import java.util.Date;

@ApplicationScoped
public class JwtUtil {

    private String subject = "User details";

    private String claimName = "username";

    private String issuer = "yestai";

    private String secret = "se.ifmo";

    private long confirmTime = 15;

    private long userTime = 20;

    private String generateToken(String email, long lifeTimeMinutes) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(lifeTimeMinutes).toInstant());

        return JWT.create()
                .withSubject(subject)
                .withClaim(claimName, email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateTokenWithConfirmExpirationTime(String email) {
        return generateToken(email, confirmTime);
    }

    public String generateTokenWithCommonUserTime(String email) {
        return generateToken(email, userTime);
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier =
                JWT.require(Algorithm.HMAC256(secret)).withSubject(subject).withIssuer(issuer).build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(claimName).asString();
    }
}
