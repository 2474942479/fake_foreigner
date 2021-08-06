package edu.zsq.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author 张
 */
@Component
public class TokenManager {

    private final String TOKEN_SIGN_KEY = "123456";

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public String createToken(String username) {
        long tokenExpiration =12 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY).compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }
}