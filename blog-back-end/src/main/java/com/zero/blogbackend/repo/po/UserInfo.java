package com.zero.blogbackend.repo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * UserRepo
 *
 * @author ZERO
 * @date 2022/12/30
 */
@TableName("user_info")
@Data
public class UserInfo {

    @TableField("id")
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;

    @TableField("email")
    private String email;

    @TableField("create_time")
    private String createTime;

    @TableField("updated_time")
    private String updatedTime;

    @TableField("isDeleted")
    private Integer isDeleted;
}
