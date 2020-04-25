package org.clm.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.clm.demo.exception.CommonException;
import org.clm.demo.mvc.primiary.entity.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
//下面这两个注解，是让实体从配置文件读取信息
//@EnableConfigurationProperties(JwtUtil.class)
//@ConfigurationProperties("jwt.config")
@Component
public class JwtUtil {
    //签名私钥
    private Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //签名失效时间
    private long ttl=3600000;

    /**
     * jwt生成token
     * @param map
     * @return
     */
    public String createJwt(User user, Map<String,Object> map){
        long now = System.currentTimeMillis();
        long exp = now+ttl;

        JwtBuilder jwtBuilder = Jwts.builder().setId(user.getPassword())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .signWith(key);

        for (Map.Entry<String,Object> entry:map.entrySet()) {
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        return token;
    }

    public Claims parseJwt(String token) throws CommonException {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new CommonException("token解析异常，请传入正确的token");
        }
        return claims;
    }
}
