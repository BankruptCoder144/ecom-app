package com.example.orderservice.util;

import com.example.orderservice.exceptions.AppException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;


public class TokenUtil {

    private static final String SECRET = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";

    public static int getUserIdFromToken(final String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey(
                getKey()).build().parseSignedClaims(token).getPayload().get("id").toString());
    }

    public static String extractAuthToken(String authHeader) throws AppException {
        if(authHeader == null) {
            throw new AppException("Auth Token Missing", HttpStatus.FORBIDDEN);
        }
        if(authHeader!=null && authHeader.startsWith("Bearer")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    private static Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
