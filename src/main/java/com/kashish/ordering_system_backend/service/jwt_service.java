package com.kashish.ordering_system_backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class jwt_service {
    private final String secret_key = "kashish_ordering_system_secret_key_2024!!";

    private SecretKey get_key() {
        return Keys.hmacShaKeyFor(secret_key.getBytes());

    }

    public String generate_token(String email) {
        return Jwts.builder().subject(email).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(get_key()).compact();
    }
    public String extract_email(String token){
        return Jwts.parser().verifyWith(get_key()).build().parseSignedClaims(token).getPayload().getSubject();

    }
    public  boolean validate_token(String token, String email){
        String extracted_email = extract_email(token);
        return extracted_email.equals(email);
    }
}
