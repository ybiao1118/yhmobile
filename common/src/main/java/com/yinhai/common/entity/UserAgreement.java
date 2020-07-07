package com.yinhai.common.entity;

import lombok.Data;

/**
 * @author lj
 * @since 2020/03/07
 */
@Data
public class UserAgreement {
    /**协议id*/
    private String  title_id;
    /**协议标题*/
    private String  title;
    /**协议正文*/
    private String  main_body;
    /**协议发布时间*/
    private String  release_date;
}
