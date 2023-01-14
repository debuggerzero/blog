package com.zero.blogbackend.repo.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zero.blogbackend.repo.UserInfoRepo;
import com.zero.blogbackend.repo.mapper.UserInfoMapper;
import com.zero.blogbackend.model.po.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 用户信息实现类
 *
 * @author ZERO
 * @date 2023/1/13
 */
@Repository("userInfoRepoImpl")
@RequiredArgsConstructor
public class UserInfoRepoImpl implements UserInfoRepo {

    private final UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByAccount(String account) {

        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getAccount, account);

        return userInfoMapper.selectOne(queryWrapper);
    }
}
