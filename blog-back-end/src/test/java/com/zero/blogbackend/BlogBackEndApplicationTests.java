package com.zero.blogbackend;

import com.zero.blogbackend.model.command.LoginCommand;
import com.zero.blogbackend.model.po.UserInfo;
import com.zero.blogbackend.model.vo.UserInfoVO;
import com.zero.blogbackend.repo.UserInfoRepo;
import com.zero.blogbackend.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class BlogBackEndApplicationTests {

    @Resource(name = "userInfoRepoImpl")
    private UserInfoRepo userInfoRepo;

    @Resource
    private UserInfoService userInfoService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUserInfoByName() {
        UserInfo zero = userInfoRepo.getUserInfoByAccount("zero");
        System.out.println(zero);
    }

    @Test
    void testLogin() {
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setAccount("zero");
        loginCommand.setPassword(password);
        UserInfoVO zero = userInfoService.login(loginCommand);
        System.out.println(zero);
    }

}
