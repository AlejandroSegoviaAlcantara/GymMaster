package com.gymmaster.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "bWlfY2xhdmVfc2VjcmV0YV8xMjM0NTY3ODkwMTIzNDU2Nzg5MA=="; // Debe estar en base64
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 4; // 4 horas

    private static SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // ✅ devuelve SecretKey, requerido por .verifyWith()
    }

    public static String generarToken(Long userId, String rol) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }

    public static boolean esTokenValido(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Long getIdUsuario(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public static String getRol(String token) {
        return getClaims(token).get("rol", String.class);
    }
}
