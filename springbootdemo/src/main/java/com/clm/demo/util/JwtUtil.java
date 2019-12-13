package com.clm.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/*@EnableConfigurationProperties(JwtUtil.class)
@ConfigurationProperties("jwt.config")*/
public class JwtUtil {
    //签名私钥
    private Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //签名失效时间
    private long ttl=3600000;

    /**
     * jwt生成token
     * @param id
     * @param name
     * @param map
     * @return
     */
    public String createJwt(String id , String name, Map<String,Object> map){
        long now = System.currentTimeMillis();
        long exp = now+ttl;

        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(key);

        for (Map.Entry<String,Object> entry:map.entrySet()) {
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        return token;
    }

    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        return claims;
    }
}
