package com.zero.blogbackend.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_articles")
public class Article {

    @TableField("article_id")
    private ArticleInfo articleInfo;

    @TableField("user_id")
    private UserInfo userInfo;

}
