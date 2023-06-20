package com.eco.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eco.user.entity.SysUser;
import com.eco.user.mapper.SysUserMapper;
import com.eco.user.service.ISysUserService;
import org.springframework.stereotype.Service;


/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author bcro
 * @since 2023-05-11 17:51:45
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


}
