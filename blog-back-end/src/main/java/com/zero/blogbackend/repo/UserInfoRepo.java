package com.zero.blogbackend.repo;

import com.zero.blogbackend.model.po.UserInfo;

/**
 * 用户信息
 *
 * @author ZERO
 * @date 2023/1/13
 */
public interface UserInfoRepo {

    /**
     * 通过账号获取用户信息
     * @param account 账号
     * @return 用户信息
     */
    UserInfo getUserInfoByAccount(String account);

}
