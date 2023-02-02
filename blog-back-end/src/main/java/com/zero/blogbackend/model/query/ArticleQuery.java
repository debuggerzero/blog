package com.zero.blogbackend.model.query;

import com.zero.blogbackend.model.common.Paging;
import lombok.Data;

/**
 * 文章查询器
 *
 * @author ZERO
 * @date 2023/1/28
 */
@Data
public class ArticleQuery {
    private Integer userId;

    private Integer isDelete = 0;

    private Paging paging;
}
