package com.eco.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eco.common.result.Result;
import com.eco.common.utils.IdUtils;
import com.eco.user.entity.SysUser;
import com.eco.user.request.SysUserReq;
import com.eco.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author bcro
 * @title: UserController
 * @description: TODO
 * @date 2023/4/23 9:48
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ISysUserService sysUserService;

    @RequestMapping("/page")
    public Result<Page<SysUser>> list(@RequestBody SysUserReq req) {
        Page<SysUser> page = sysUserService.page(new Page<>(req.getPageNum(), req.getPageSize()));
        return Result.success(page);
    }

    @RequestMapping("/add")
    public Result<Boolean> add(@RequestBody SysUserReq req) {
        SysUser sysUser = BeanUtil.copyProperties(req, SysUser.class);
        sysUser.setId(IdUtils.getId());
        sysUser.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));
        return Result.success(sysUserService.save(sysUser));
    }


}
