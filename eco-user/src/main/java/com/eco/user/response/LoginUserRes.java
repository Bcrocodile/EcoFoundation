package com.eco.user.response;

import com.eco.user.entity.SysUser;
import lombok.Data;

/**
 * @author bcro
 * @title: LoginUserRes
 * @description: TODO
 * @date 2023/5/16 14:44
 */
@Data
public class LoginUserRes {


    private SysUser user;

    private String token;

    private String accessToken;

}
