package com.scy.stock.vo.req;

import lombok.Data;

@Data
public class LoginReqVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 保存redis随机码的key
     */
    private String rkey;
}