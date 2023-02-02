package com.zero.blogbackend.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 文章信息
 *
 * @author ZERO
 * @date 2023/1/18
 */
@Data
@TableName("articles_info")
public class ArticleInfo {
    @TableId
    private Integer id;

    @TableField("article_id")
    private String articleId;

    @TableField("article_title")
    private String articleTitle;

    @TableField("article_image")
    private String articleImage;

    @TableField("comment")
    private String comment;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;

    @TableField("is_delete")
    private Integer isDelete;
}
