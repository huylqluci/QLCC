//package com.nhom43.quanlychungcubackendgradle.share.security.service;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.io.Encoders;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.security.*;
//import java.sql.Date;
//import java.time.Instant;
//
//import static io.jsonwebtoken.Jwts.parserBuilder;
//import static io.jsonwebtoken.SignatureAlgorithm.HS512;
//import static java.util.Date.from;
//
//@Service
//public class JwtProvider {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
//
//    @Value("${nhom43.app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${nhom43.app.jwtExpirationMs}")
//    private Long jwtExpirationInMillis;
//
//    // Tìm hiểu thêm về KeyStore +> PrivateKey và PublicKey tại trang https://github.com/jwtk/jjwt#jws-key-create
//    private Key getSecretKey() {
////        if (this.jwtSecret == null || this.jwtSecret.equals("")) {
////            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
////            this.jwtSecret = Encoders.BASE64.encode(key.getEncoded());
//////            System.out.println(this.jwtSecret);
////        }
//        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
////        System.out.println(keyBytes);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateJwtToken(Authentication authentication) {
//        User userPrincipal = (User) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(from(Instant.now()))
//                .signWith(HS512, getSecretKey())
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//                .compact();
//    }
//
//    public String generateJwtTokenWithUsername(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(from(Instant.now()))
//                .signWith(HS512, getSecretKey())
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//                .compact();
//    }
//
//    public String getUsernameFromJwtToken(String token) {
//        Claims claims = parserBuilder()
//                .setSigningKey(getSecretKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    public Long getJwtExpirationInMillis() {
//        return jwtExpirationInMillis;
//    }
//
//    public boolean validateJwtToken(String jwtString) {
//        try {
//            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(jwtString);
//            return true;
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token (Mã token JWT không hợp lệ): {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired (Mã token JWT hết hạn): {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported (Mã token JWT không đc hỗ trợ): {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty (Chuỗi xác nhận quyền sở hữu trống): {}", e.getMessage());
//        }
//        return false;
//    }
//
//}

package com.nhom43.quanlychungcubackendgradle.share.security.service;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Date.from;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${nhom43.app.jwtSecret}")
    private String jwtSecret;

    @Value("${nhom43.app.jwtExpirationMs}")
    private Long jwtExpirationInMillis;


    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(HS512, this.jwtSecret)
                .setExpiration(from(Instant.now().plusMillis(this.jwtExpirationInMillis)))
                .compact();
    }

    public String generateJwtTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(HS512, this.jwtSecret)
                .setExpiration(Date.from(Instant.now().plusMillis(this.jwtExpirationInMillis)))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature (Chữ kí JWT không hợp lệ) {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token (Mã token JWT không hợp lệ): {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired (Mã token JWT hết hạn): {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported (Mã token JWT không đc hỗ trợ): {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty (Chuỗi xác nhận quyền sở hữu trống): {}", e.getMessage());
        }
        return false;
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}