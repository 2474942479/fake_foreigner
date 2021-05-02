package edu.zsq.utils.jwt;

import edu.zsq.utils.exception.core.ExFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 张
 */
public class JwtUtils {

    /**
     * 过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 12;

    /**
     * 秘钥
     */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成token字符串
     *
     * @param id       用户id
     * @param nickname 用户名称
     * @return token字符串
     */
    public static String getJwtToken(String id, String nickname) {

        return Jwts.builder()
                // 1. 设置token头部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置分类
                .setSubject("online-user")
                // 设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 2. 设置token主体部分(存储用户信息)
                .claim("id", id)
                .claim("nickname", nickname)
                // 3. 设置token签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken token字符串
     * @return 是否有效
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            // 对Jwt解析
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 从token字符串中获取用户id
     *
     * @param request 请求
     * @return 用户id
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            throw ExFactory.throwBusiness("token 已失效");
        }
        // 解析Jwt
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        return (String) claimsJws.getBody().get("id");
    }
}