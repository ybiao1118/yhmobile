package com.yinhai.common.entity;

import lombok.Data;

/**用户信息实体类
 * @author yanbiao
 * @since 2019/11/8 17:54
 */
@Data
public class UserInfo {
    /** id*/
    private String id;
    /** 用户名*/
    private String userName;
    /** 密码*/
    private String passWord;
    /** 性别*/
    private String sex;
    /** 姓名*/
    private String name;
    /** 年龄*/
    private String age;

}
