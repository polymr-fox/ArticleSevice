package com.mrfox.senyast4745.articleservice.security.jwt;

import com.mrfox.senyast4745.articleservice.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secretKey:hello}")
    private String secretKey = "hello";


    private final CustomUserDetailsService userDetailsService;



    @Autowired
    public JwtTokenProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    boolean validateToken(String token) {
        token = token.trim();
        Jws<Claims> claims = null;
        try {
            LOGGER.info("header token is: " + token);
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            LOGGER.info("claims " + claims.getBody().getSubject());
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {

            LOGGER.warn("Claims in " + e.getClass().getSimpleName() + " " + claims);

            LOGGER.warn(e.getMessage());
            if (claims != null) {
                throw new InvalidJwtAuthenticationException("Expired JWT token " + claims.getBody().getExpiration());
            }
            throw new InvalidJwtAuthenticationException("Expired JWT token null");
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Claims in " + e.getClass().getSimpleName() + " " + claims);

            LOGGER.warn(e.getMessage());
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        } catch (Exception e) {
            LOGGER.warn("Claims in " + e.getClass().getSimpleName() + " " + claims);
            e.printStackTrace();
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }




}
