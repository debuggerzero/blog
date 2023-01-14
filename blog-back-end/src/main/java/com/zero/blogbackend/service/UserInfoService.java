package com.zero.blogbackend.service;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.command.LoginCommand;
import com.zero.blogbackend.model.converter.ToUserInfoVoConverter;
import com.zero.blogbackend.model.po.UserInfo;
import com.zero.blogbackend.model.vo.UserInfoVO;
import com.zero.blogbackend.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户信息业务层
 *
 * @author ZERO
 * @date 2023/1/13
 */
@Service
@RequiredArgsConstructor
public class UserInfoService {

    @Resource(name = "userInfoRepoImpl")
    private final UserInfoRepo userInfoRepo;

    public UserInfoVO login(LoginCommand loginCommand) {
        UserInfo userInfo = userInfoRepo.getUserInfoByAccount(loginCommand.getAccount());
        if (Objects.isNull(userInfo) || !userInfo.getPassword().equals(loginCommand.getPassword())) {
            throw new AssertionException(500001, "登录失败，用户账户或者密码输入错误");
        }

        return ToUserInfoVoConverter.CONVERTER.toUserInfoVO(userInfo);
    }

}
