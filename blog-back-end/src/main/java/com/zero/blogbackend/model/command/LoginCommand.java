package com.zero.blogbackend.model.command;

import lombok.Data;

/**
 * UserInfoCommand
 *
 * @author ZERO
 * @date 2023/1/13
 */
@Data
public class LoginCommand {

    /**
     * 账号
     */
    String account;

    /**
     * 密码
     */
    String password;

}
