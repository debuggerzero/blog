package com.zero.blogbackend.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PutArticleCommand
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommand {

    /**
     * 用户 Id
     */
    private Integer userId;

    /**
     * id
     */
    private Integer id;

    /**
     * 文章 Id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章图片
     */
    private String articleImage;

    /**
     * 文章描述
     */
    private String comment;

    /**
     * 创建时间
     */
    private String createTime;

}
