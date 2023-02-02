package com.zero.blogbackend.controller;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.command.LoginCommand;
import com.zero.blogbackend.model.vo.UserInfoVO;
import com.zero.blogbackend.service.UserInfoService;
import com.zero.blogbackend.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * UserInfoController
 *
 * @author ZERO
 * @date 2023/1/13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserInfoService userInfoService;

    @PostMapping("/password")
    public UserInfoVO loginByAccount(@RequestBody LoginCommand loginCommand) {
        if (StringUtil.isEmpty(loginCommand.getAccount()) || StringUtil.isEmpty(loginCommand.getPassword())) {
            throw new AssertionException(500002, "账号、密码不能为空");
        }
        return userInfoService.login(loginCommand);
    }

}
