package com.example.apigatewayservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
public class TokenUtil {

    private static final String SECRET = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";

    public boolean validateTokenForRole(final String token, final String role) {
        return Jwts.parser().setSigningKey(
                getKey()).build().parseSignedClaims(token).getPayload().get("role").equals(role);
    }

    public String extractAuthToken(String authHeader) {
        if(authHeader!=null && authHeader.startsWith("Bearer")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
