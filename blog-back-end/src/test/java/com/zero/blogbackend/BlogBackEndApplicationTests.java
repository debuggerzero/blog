package com.zero.blogbackend;

import com.zero.blogbackend.model.command.LoginCommand;
import com.zero.blogbackend.model.common.Paging;
import com.zero.blogbackend.model.po.ArticleInfo;
import com.zero.blogbackend.model.po.UserInfo;
import com.zero.blogbackend.model.query.ArticleQuery;
import com.zero.blogbackend.model.vo.ArticleInfoVO;
import com.zero.blogbackend.model.vo.UserInfoVO;
import com.zero.blogbackend.repo.ArticleRepo;
import com.zero.blogbackend.repo.UserInfoRepo;
import com.zero.blogbackend.repo.mapper.ArticleMapper;
import com.zero.blogbackend.service.ArticleService;
import com.zero.blogbackend.service.CosService;
import com.zero.blogbackend.service.UserInfoService;
import com.zero.blogbackend.utils.StringUtil;
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

}
