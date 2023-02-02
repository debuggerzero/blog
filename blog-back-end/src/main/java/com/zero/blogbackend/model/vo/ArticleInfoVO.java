package com.zero.blogbackend.model.vo;

import lombok.Data;

/**
 * ArticleInfoVO
 *
 * @author ZERO
 * @date 2023/1/28
 */
@Data
public class ArticleInfoVO {

    private Integer id;

    private String articleId;

    private String articleTitle;

    private String articleContent;

    private String articleImage;

    private String comment;

    private String createTime;

    private String updateTime;
}
