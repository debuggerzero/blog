package com.zero.blogbackend.model.converter;

import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Article --> ArticleVO
 *
 * @author ZERO
 * @date 2023/1/28
 */
@Mapper
public interface ToArticleVoConverter {
    ToArticleVoConverter CONVERTER = Mappers.getMapper(ToArticleVoConverter.class);

    /**
     * toArticleVO
     * @param article article
     * @return ArticleVO
     */
    @Mapping(source = "articleInfo", target = "articleInfoVO")
    @Mapping(source = "userInfo", target = "userInfoVO")
    ArticleVO toArticleVO(Article article);
}
