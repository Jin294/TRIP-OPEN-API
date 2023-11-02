package com.ssafy.i5i.hotelAPI.common.userSecurity;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtill {

    @Value("${jwt.secretKey}")
    private String jwtSecretKeyValue;

    private static String jwtSecret;
    @PostConstruct
    private void init(){
        jwtSecret = jwtSecretKeyValue;
    }

    public static String generateToken(String userId, String subject, Long expirationTime){
        Instant now = Instant.now();
        Instant expirationInstant = now.plusMillis(expirationTime);

        Claims claims = Jwts.claims();
        claims.put("userId",userId);
        claims.setExpiration(Date.from(expirationInstant));
        claims.setSubject(subject);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("regDate", now.toEpochMilli())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public static String generateAccessToken(String userId){
        Long expirationTime = 1000L * 60 * 20; // 20분
        return generateToken(userId, "access-token", expirationTime);
    }

    public static String generateRefreshToken(String userId){
        Long expirationTime = 1000L * 60 * 60; // 1시간
        return generateToken(userId, "refresh-token", expirationTime);
    }

    private static boolean isExpired(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody()
                .getExpiration().before(new Date());
    }

    public static Claims getPayloadAndCheckExpired(String jwt){
        if(isExpired(jwt)) throw new CommonException(ExceptionType.JWT_TOKEN_EXPIRED);

        try{
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();
        } catch (Exception e){
            e.printStackTrace();
            throw new CommonException(ExceptionType.JWT_PARSER_FAILED);
        }
    }

}
