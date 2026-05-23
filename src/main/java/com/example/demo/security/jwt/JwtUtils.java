package com.example.demo.security.jwt;

import com.example.demo.services.UserDetailImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.MalformedInputException;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${projeto.jwtSecret}")
    private String jwtScret;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateTokenFromUserDetailsImplt(UserDetailImpl userDetail) {
        return Jwts.builder().setSubject(userDetail.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(getSigningKey(),  SignatureAlgorithm.HS512).compact();
    }

    public Key getSigningKey() {
        SecretKey key = Keys.hmacShaKeyFor(jwtScret.getBytes());
        return key;
    }

    public String getUsernameToken(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        }

        catch (MalformedJwtException e) {
            System.out.println("Token invalido" + e.getMessage());
        }
        catch (ExpiredJwtException e) {
            System.out.println("Token invalido" + e.getMessage());
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Token invalido" + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Token invalido" + e.getMessage());
        }

        return false;

    }
}
