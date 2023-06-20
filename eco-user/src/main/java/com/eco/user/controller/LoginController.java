package com.eco.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eco.common.result.Result;
import com.eco.user.entity.SysUser;
import com.eco.user.response.LoginUserRes;
import com.eco.user.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author bcro
 * @title: UserController
 * @description: TODO
 * @date 2023/4/23 9:48
 */
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final ISysUserService sysUserService;



    @RequestMapping("doLogin")
    public Result<LoginUserRes> doLogin(String name, String pwd) {
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, name)
                .eq(SysUser::getPassword, pwd)
        );
        if (user != null) {
            StpUtil.login(user.getUserName());
            LoginUserRes loginUserRes = new LoginUserRes();
            loginUserRes.setUser(user);
            loginUserRes.setAccessToken(user.getAccessToken());
            loginUserRes.setToken(StpUtil.getTokenValue());
            return Result.success(loginUserRes);
        }
        return Result.failed("登录失败");
    }

    @RequestMapping("isLogin")
    public Result isLogin() {
        boolean login = StpUtil.isLogin();
        return Result.success(login);
    }

    @RequestMapping("tokenInfo")
    public Result tokenInfo() {
        return Result.success(StpUtil.getTokenInfo());
    }

    @RequestMapping("logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }

}
