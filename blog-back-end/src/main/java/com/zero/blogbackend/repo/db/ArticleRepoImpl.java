package com.zero.blogbackend.repo.db;

import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.po.ArticleInfo;
import com.zero.blogbackend.model.query.ArticleQuery;
import com.zero.blogbackend.repo.ArticleRepo;
import com.zero.blogbackend.repo.mapper.ArticleInfoMapper;
import com.zero.blogbackend.repo.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleRepoImpl
 *
 * @author ZERO
 * @date 2023/1/27
 */
@RequiredArgsConstructor
@Repository("articleRepoImpl")
public class ArticleRepoImpl implements ArticleRepo {

    private final ArticleMapper articleMapper;

    private final ArticleInfoMapper articleInfoMapper;

    @Override
    public List<Article> queryArticles(ArticleQuery articleQuery) {
        return articleMapper.queryArticles(articleQuery);
    }

    @Override
    public Article queryOneArticle(Integer articleId) {
        return articleMapper.queryOneArticle(articleId);
    }

    @Override
    public Integer insertArticle(Article article) {
        return articleMapper.insertArticle(article);
    }

    @Override
    public Integer updateArticle(ArticleInfo articleInfo) {
        return articleInfoMapper.updateById(articleInfo);
    }

    @Override
    public Integer deleteArticle(Integer id) {
        return articleInfoMapper.deleteById(id);
    }

    @Override
    public Long totalArticleCount(Integer userId, Integer isDelete) {
        return articleMapper.totalArticleCount(userId, isDelete);
    }

    @Override
    public Integer deleteArticleComplete(String articleId) {
        return articleMapper.deleteArticleComplete(articleId);
    }

    @Override
    public Integer restoreArticle(Integer id) {
        return articleInfoMapper.restoreArticle(id);
    }

}
