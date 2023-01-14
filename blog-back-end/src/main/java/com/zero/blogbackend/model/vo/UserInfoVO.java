package com.zero.blogbackend.model.vo;

import lombok.Data;

/**
 * UserInfoVO
 *
 * @author ZERO
 * @date 2023/1/13
 */
@Data
public class UserInfoVO {

    private Integer id;

    private String username;

    private String account;

    private String avatar;

    private String email;

    private String createTime;

}
