package com.zero.blogbackend.repo;

import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.po.ArticleInfo;
import com.zero.blogbackend.model.query.ArticleQuery;

import java.util.List;

/**
 * ArticleRepo
 *
 * @author ZERO
 * @date 2023/1/27
 */
public interface ArticleRepo {

    /**
     * 查询文章列表
     * @param articleQuery 用户 Id
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
     * 更新文章信息
     * @param articleInfo 文章信息
     * @return 操作成功的列数
     */
    Integer updateArticle(ArticleInfo articleInfo);

    /**
     * 删除文章
     * @param id 文章 Id
     * @return 操作成功的列数
     */
    Integer deleteArticle(Integer id);

    /**
     * 文章总数
     * @param userId 用户 Id
     * @param isDelete 是否删除
     * @return 文章条数
     */
    Long totalArticleCount(Integer userId, Integer isDelete);

    /**
     * 彻底删除文章
     * @param articleId 文章
     * @return 操作成功的条数
     */
    Integer deleteArticleComplete(String articleId);

    /**
     * 恢复文章
     * @param id id
     * @return 成功操作的行数
     */
    Integer restoreArticle(Integer id);

}
