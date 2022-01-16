package com.godzillajim.betterprogramming.config;

import com.godzillajim.betterprogramming.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final String jwtSecret = "bezKoderSecretKey";

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        int jwtExpiration = 86400000;
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token){
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
            throw new BadCredentialsException(String.format("Invalid JWT signature: {%s}", e.getMessage()));
        } catch (MalformedJwtException e) {
            throw new BadCredentialsException(String.format("Invalid JWT token: {%s}", e.getMessage()));
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException(String.format("JWT token is expired: {%s}", e.getMessage()));
        } catch (UnsupportedJwtException e) {
            throw new BadCredentialsException(String.format("JWT token is unsupported: {%s}", e.getMessage()));
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(String.format("JWT claims string is empty: {%s}", e.getMessage()));
        }
    }
}
