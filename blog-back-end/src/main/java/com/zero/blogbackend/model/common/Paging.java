package com.zero.blogbackend.model.common;

import lombok.Data;

/**
 * 分页
 *
 * @author ZERO
 * @date 2023/1/28
 */
@Data
public class Paging {

    /**
     * 页码
     */
    private Long pageNumber = 0L;

    /**
     * 页大小
     */
    private Long pageSize = 10L;
}
