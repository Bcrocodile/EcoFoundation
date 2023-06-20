package com.eco.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 权限表(SysPermission) DO层
 *
 * @author bcro
 * @since 2023-05-12 13:52:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysPermission对象", description = "权限表")
@TableName("sys_permission")
public class SysPermission extends Model<SysPermission> implements Serializable {
    private static final long serialVersionUID = -36810616532561204L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     **/
    @ApiModelProperty(value = "权限名称")
    @TableField(value = "`name`")
    private String name;

    /**
     * 菜单模块ID
     * 
     **/
    @ApiModelProperty(value = "菜单模块ID")
    @TableField(value = "`menu_id`") 
    private Long menuId;

    /**
     * URL权限标识
     **/
    @ApiModelProperty(value = "URL权限标识")
    @TableField(value = "`url_perm`")
    private String urlPerm;

    /**
     * 按钮权限标识
     **/
    @ApiModelProperty(value = "按钮权限标识")
    @TableField(value = "`btn_perm`")
    private String btnPerm;

    /**
    * 
    **/
    @ApiModelProperty(value = "")
    @TableField(value = "`create_time`")
    private Date createTime;

    /**
    * 
    **/
    @ApiModelProperty(value = "")
    @TableField(value = "`update_time`")
    private Date updateTime;

    /**
    * 
    **/
    @ApiModelProperty(value = "")
    @TableField(value = "`deleted`")
    private Integer deleted;

}
