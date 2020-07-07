package com.yinhai.common.util;


import com.yinhai.common.config.CommonConfig;
import com.yinhai.common.entity.JwtAccount;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;


/** jwt生成登录token类
 * @author yanbiao
 * @since 2019/11/14 13:25
 */
@SuppressWarnings("ALL")
@Component
public class JwtUtil {
   @Autowired
    private CommonConfig config;
    /**
     *   json web token 签发
     * @param id 令牌ID
     * @param subject 用户ID
     * @return java.lang.String
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public  String createJwt(String id, String subject) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间戳
        Long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secreKeyBytes = DatatypeConverter.parseBase64Binary(config.jwtSignKey);
        JwtBuilder jwtBuilder = Jwts.builder();
        if (!StringUtils.isEmpty(id)) {
            jwtBuilder.setId(id);
        }
        if (!StringUtils.isEmpty(subject)) {
            jwtBuilder.setSubject(subject);
        }
        jwtBuilder.setIssuer("token-server");
        // 设置签发时间
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        // 设置到期时间
        jwtBuilder.setExpiration(new Date(currentTimeMillis+config.jwtRefreshTime*60*60*1000));
        // 压缩，可选GZIP
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        // 加密设置
        jwtBuilder.signWith(signatureAlgorithm,secreKeyBytes);
        return jwtBuilder.compact();
    }

    /**
     * 验签JWT
     * @param jwt json web token
     */
    public  JwtAccount parseJwt(String jwt)  {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(config.jwtSignKey))
                .parseClaimsJws(jwt)
                .getBody();
        JwtAccount jwtAccount = new JwtAccount();
        //令牌ID
        jwtAccount.setTokenId(claims.getId());
        // 客户标识
        jwtAccount.setAppId(claims.getSubject());
        // 签发者
        jwtAccount.setIssuer(claims.getIssuer());
        // 签发时间
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        // 接收方
        jwtAccount.setAudience(claims.getAudience());
        return jwtAccount;
    }
}
