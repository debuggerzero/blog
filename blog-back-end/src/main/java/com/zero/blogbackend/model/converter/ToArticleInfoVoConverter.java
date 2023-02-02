package com.zero.blogbackend.model.converter;

import com.zero.blogbackend.model.po.Article;
import com.zero.blogbackend.model.vo.ArticleInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * article --> articleInfoVO
 *
 * @author ZERO
 * @date 2023/1/28
 */
@Mapper
public interface ToArticleInfoVoConverter {
    ToArticleInfoVoConverter CONVERTER = Mappers.getMapper(ToArticleInfoVoConverter.class);

    /**
     * toArticleInfoVO
     * @param article article
     * @return ArticleInfoVO
     */
    @Mapping(source = "articleInfo.id", target = "id")
    @Mapping(source = "articleInfo.articleId", target = "articleId")
    @Mapping(source = "articleInfo.articleTitle", target = "articleTitle")
    @Mapping(source = "articleInfo.articleImage", target = "articleImage")
    @Mapping(source = "articleInfo.comment", target = "comment")
    @Mapping(source = "articleInfo.createTime", target = "createTime")
    @Mapping(source = "articleInfo.updateTime", target = "updateTime")
    ArticleInfoVO toArticleInfoVO(Article article);
}
