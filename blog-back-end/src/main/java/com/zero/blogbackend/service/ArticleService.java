package com.zero.blogbackend.service;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.command.ArticleCommand;
import com.zero.blogbackend.model.converter.ToArticleConverter;
import com.zero.blogbackend.model.converter.ToArticleInfoVoConverter;
import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.query.ArticleQuery;
import com.zero.blogbackend.model.vo.ArticleInfoVO;
import com.zero.blogbackend.repo.ArticleRepo;
import com.zero.blogbackend.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ArticleService
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    @Resource(name = "articleRepoImpl")
    private final ArticleRepo articleRepo;

    private final CosService cosService;

    private Article toArticle(ArticleCommand articleCommand) {
        String newKey = cosService.getImageKey(articleCommand.getArticleImage());
        articleCommand.setArticleImage(newKey);
        return ToArticleConverter.CONVERTER.toArticle(articleCommand);
    }

    @Transactional(rollbackFor = Exception.class)
    public String putArticle(ArticleCommand articleCommand) {

        Article article = toArticle(articleCommand);
        article.getArticleInfo().setCreateTime(StringUtil.getCurrentTime());
        article.getArticleInfo().setUpdateTime(StringUtil.getCurrentTime());

        Integer length = articleRepo.insertArticle(article);
        if (length == 0) {
            throw new AssertionException(500004, "上传失败");
        }

        String key = cosService.getArticleKey(articleCommand.getUserId().toString(), articleCommand.getArticleId());
        return cosService.putArticle(articleCommand.getArticleContent(), key);
    }

    public String getArticleContent(Integer userId, String articleId) {
        String key = cosService.getArticleKey(userId.toString(), articleId);
        if (StringUtil.isEmpty(key)) {
            throw new AssertionException(500004, "获取失败");
        }
        return cosService.getObject(key);
    }

    public List<ArticleInfoVO> queryArticle(ArticleQuery articleQuery) {

        List<Article> articleList = articleRepo.queryArticles(articleQuery);

        for (Article article : articleList) {
            String key = cosService.getImageUrl(article.getArticleInfo().getArticleImage());
            article.getArticleInfo().setArticleImage(key);
        }

        return articleList
                .stream()
                .map(ToArticleInfoVoConverter.CONVERTER::toArticleInfoVO)
                .collect(Collectors.toList());
    }

    public Long totalArticleCount(Integer userId, Integer isDelete) {
        return articleRepo.totalArticleCount(userId, isDelete);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteArticle(Integer id) {
        return articleRepo.deleteArticle(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateArticle(ArticleCommand articleCommand) {

        Article article = toArticle(articleCommand);
        article.getArticleInfo().setUpdateTime(StringUtil.getCurrentTime());

        Integer length = articleRepo.updateArticle(article.getArticleInfo());
        if (length == 0) {
            throw new AssertionException(500004, "更新失败");
        }
        String articleKey = cosService.getArticleKey(articleCommand.getUserId().toString(), articleCommand.getArticleId());
        cosService.putArticle(articleCommand.getArticleContent(), articleKey);

        return length;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteArticleComplete(String userId, String articleId) {

        Integer length = articleRepo.deleteArticleComplete(articleId);
        if (length == 0) {
            throw new AssertionException(500004, "删除失败");
        }
        cosService.deleteObject(cosService.getArticleKey(userId, articleId));

        return length;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer restoreArticle(Integer id) {
        return articleRepo.restoreArticle(id);
    }

}
