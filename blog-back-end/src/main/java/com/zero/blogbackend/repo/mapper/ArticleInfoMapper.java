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

}
