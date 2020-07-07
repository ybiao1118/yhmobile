package com.yinhai.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**jwt实体类
 * @author yanbiao
 * @since 2019/11/14 13:28
 */
@Data
public class JwtAccount implements Serializable {

    private static final long serialVersionUID = -895875540581785581L;

    /**
     * 令牌id
     */
    private String tokenId;
    /**
     * 客户标识（用户名、账号）
     */
    private String appId;
    /**
     * 签发者(JWT令牌此项有值)
     */
    private String issuer;
    /**
     * 签发时间
     */
    private Date issuedAt;
    /**
     * 接收方(JWT令牌此项有值)
     */
    private String audience;
    /**
     * 客户地址
     */
    private String host;

    public JwtAccount() {

    }
    public JwtAccount(String tokenId, String appId, String issuer, Date issuedAt, String audience, String roles, String perms, String host) {
        this.tokenId = tokenId;
        this.appId = appId;
        this.issuer = issuer;
        this.issuedAt = issuedAt;
        this.audience = audience;
        this.host = host;
    }
}