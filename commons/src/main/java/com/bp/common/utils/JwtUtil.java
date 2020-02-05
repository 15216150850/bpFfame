package com.bp.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

@Component
public class JwtUtil {

  private String key="bp.system";
  private Long ttl = 3600*1000*24L;

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String role) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("role", role);
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 生成JWT BY SysUser
     *
     * @param userMap
     * @return
     */
    public String createJWT(Map userMap) {
        String id=userMap.get("id").toString();
        String username=userMap.get("username").toString();
        List roleList=(List)userMap.get("roles");
        List roles=new ArrayList();
        String roleStr="";
        if(null!=roleList){
            for (int i=0;i<roleList.size();i++){
                roles.add(((Map)roleList.get(i)).get("name"));
            }
            roleStr = String.join(",", roles);
        }
        return createJWT(id,username,roleStr);
    }

    /**
     * 解析JWT
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
