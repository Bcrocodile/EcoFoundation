package com.eco.user.request;

import java.util.Date;

import com.eco.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author bcro
 * @since 2023-05-11 17:51:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUser对象", description = "用户信息表")
public class SysUserReq extends BasePageQuery {
    private static final long serialVersionUID = -24285052976459481L;

    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户名
     **/
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 密码
     **/
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 联系方式
     **/
    @ApiModelProperty(value = "联系方式")
    private String mobile;

    /**
     * 用户邮箱
     **/
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 使用结束时间
     **/
    @ApiModelProperty(value = "使用结束时间")
    private Date endTime;

    /**
     * 使用次数
     **/
    @ApiModelProperty(value = "使用次数")
    private Integer useNum;

    /**
     * 使用次数限制
     **/
    @ApiModelProperty(value = "使用次数限制")
    private Integer useNumLimit;

    /**
     * 用户状态（0禁用 1正常）
     **/
    @ApiModelProperty(value = "用户状态（0禁用 1正常）")
    private Integer status;

    /**
     * 创建时间
     **/
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
