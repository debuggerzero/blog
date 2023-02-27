package com.zero.blogbackend;

import com.zero.blogbackend.model.command.LoginCommand;
import com.zero.blogbackend.model.po.UserInfo;
import com.zero.blogbackend.model.vo.UserInfoVO;
import com.zero.blogbackend.repo.UserInfoRepo;
import com.zero.blogbackend.repo.mapper.ArticleInfoMapper;
import com.zero.blogbackend.repo.mapper.ArticleMapper;
import com.zero.blogbackend.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ArticleMapper articleMapper;

    @Test
    public void testGetArticles() {
        var articles = articleMapper.queryArticles(null);
        System.out.println(articles);
    }

    @Test
    public void testTotalArticles() {
        Long aLong = articleMapper.totalArticleCount(1002, 0);
        System.out.println(aLong);
    }

    @Test
    public void testDeleteArticles() {
        Integer string = articleMapper.deleteArticleComplete("String");
        System.out.println(string);
    }

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Test
    public void testRestoreArticles() {
        Integer integer = articleInfoMapper.restoreArticle(1001);
        System.out.println(integer);
    }

}
