package com.zero.blogbackend.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.blogbackend.model.po.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * ArticleInfoMapper
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Mapper
public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {

    /**
     * 恢复文章
     * @param id 文章 Id
     * @return 操作成功的行数
     */
    Integer restoreArticle(Integer id);

}
