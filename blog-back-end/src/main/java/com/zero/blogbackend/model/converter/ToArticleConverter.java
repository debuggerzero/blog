package com.zero.blogbackend.model.converter;

import com.zero.blogbackend.model.command.ArticleCommand;
import com.zero.blogbackend.model.po.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PutArticleCommand --> Article
 *
 * @author ZERO
 * @date 2023/1/27
 */
@Mapper
public interface ToArticleConverter {
    ToArticleConverter CONVERTER = Mappers.getMapper(ToArticleConverter.class);

    /**
     * toArticle
     * @param articleCommand putArticleCommand
     * @return Article
     */
    @Mapping(source = "userId", target = "userInfo.id")
    @Mapping(source = "id", target = "articleInfo.id")
    @Mapping(source = "articleId", target = "articleInfo.articleId")
    @Mapping(source = "articleTitle", target = "articleInfo.articleTitle")
    @Mapping(source = "articleImage", target = "articleInfo.articleImage")
    @Mapping(source = "comment", target = "articleInfo.comment")
    @Mapping(source = "createTime", target = "articleInfo.createTime")
    Article toArticle(ArticleCommand articleCommand);
}
