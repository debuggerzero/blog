package com.zero.blogbackend.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.query.ArticleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ArticleMapper
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章列表
     * @param articleQuery 文章查询器
     * @return List<Article>
     */
    List<Article> queryArticles(ArticleQuery articleQuery);

    /**
     * 查询一篇文章
     * @param articleId 文章 Id
     * @return 返回一篇文章
     */
    Article queryOneArticle(Integer articleId);


    /**
     * 添加文章
     * @param article 文章
     * @return 操作成功的列数
     */
    Integer insertArticle(Article article);

    /**
     * 获取文章总数
     * @param userId 用户 Id
     * @param isDelete 是否删除
     * @return 返回符合要求的文章
     */
    Long totalArticleCount(Integer userId, Integer isDelete);

}
