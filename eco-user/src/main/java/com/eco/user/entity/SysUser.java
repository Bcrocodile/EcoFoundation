package com.eco.user.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户信息表(SysUser) DO层
 *
 * @author bcro
 * @since 2023-05-11 17:51:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUser对象", description = "用户信息表")
@TableName("sys_user")
public class SysUser  implements Serializable {
    private static final long serialVersionUID = -24285052976459481L;

    @ApiModelProperty(value = "")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     **/
    @ApiModelProperty(value = "用户名")
    @TableField(value = "`user_name`")
    private String userName;

    /**
     * 密码
     **/
    @ApiModelProperty(value = "密码")
    @TableField(value = "`password`")
    private String password;

    /**
     * 联系方式
     **/
    @ApiModelProperty(value = "联系方式")
    @TableField(value = "`mobile`")
    private String mobile;

    /**
     * 外键token
     **/
    @ApiModelProperty(value = "外键token")
    @TableField(value = "`access_token`")
    private String accessToken;

    /**
     * 用户邮箱
     **/
    @ApiModelProperty(value = "用户邮箱")
    @TableField(value = "`email`")
    private String email;

    /**
     * 使用结束时间
     **/
    @ApiModelProperty(value = "使用结束时间")
    @TableField(value = "`end_time`")
    private Date endTime;

    /**
     * 使用次数
     **/
    @ApiModelProperty(value = "使用次数")
    @TableField(value = "`use_num`")
    private Integer useNum = 0;

    /**
     * 使用次数限制
     **/
    @ApiModelProperty(value = "使用次数限制")
    @TableField(value = "`use_num_limit`")
    private Integer useNumLimit;

    /**
     * 用户状态（0禁用 1正常）
     **/
    @ApiModelProperty(value = "用户状态（0禁用 1正常）")
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 创建时间
     **/
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "`create_time`")
    private Date createTime;

    public void setNum() {
        useNum++;
    }

}
