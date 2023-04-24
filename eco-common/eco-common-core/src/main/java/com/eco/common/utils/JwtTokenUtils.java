package com.eco.common.utils;

import java.util.Date;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT工具类
 */
@Slf4j
public class JwtTokenUtils {

    private static String encryptionKey;

    /**
     * 创建jwt
     * @param subject
     * @param ttlMillis
     * @param encryptionKey 前缀
     * @return
     */
    public static String createJWT(String subject, long ttlMillis,String encryptionKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, encryptionKey);
        if (ttlMillis > 0) {
            long expMillis = System.currentTimeMillis() + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public static Boolean isExpired(String token) {
        try {
            final Claims claims = parseJWT(token);
            return claims.getExpiration().before(new Date());
        }catch (Exception e) {
            log.error("解析JWT失败, token = {}", token);
        }
        return true;
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(encryptionKey)
                .parseClaimsJws(jwt).getBody();
    }

}
