package com.zero.blogbackend.controller;

import com.zero.blogbackend.exception.AssertionException;
import com.zero.blogbackend.model.command.ArticleCommand;
import com.zero.blogbackend.model.query.ArticleQuery;
import com.zero.blogbackend.model.vo.ArticleInfoVO;
import com.zero.blogbackend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * ArticleController
 *
 * @author ZERO
 * @date 2023/1/28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleManageController {

    private final ArticleService articleService;

    @PostMapping("/put")
    public String putArticle(@RequestBody ArticleCommand articleCommand) {
        if (Objects.isNull(articleCommand)) {
            throw new AssertionException(500004, "上传失败");
        }
        return articleService.putArticle(articleCommand);
    }

    @PostMapping("/query")
    public List<ArticleInfoVO> queryArticle(@RequestBody ArticleQuery articleQuery) {
        Long pageNumber = articleQuery.getPaging().getPageNumber();
        Long pageSize = articleQuery.getPaging().getPageSize();
        articleQuery.getPaging().setPageNumber((pageNumber - 1) * pageSize);
        return articleService.queryArticle(articleQuery);
    }

    @GetMapping("/total/{userId}/{isDelete}")
    public Long getTotalArticleCount(@PathVariable Integer userId, @PathVariable Integer isDelete) {
        if (isDelete == null) {
            isDelete = 0;
        }
        return articleService.totalArticleCount(userId, isDelete);
    }

    @DeleteMapping("/delete/{id}")
    public Integer deleteArticle(@PathVariable Integer id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping("/content/{userId}/{articleId}")
    public String getArticleContent(@PathVariable Integer userId, @PathVariable String articleId) {
        return articleService.getArticleContent(userId ,articleId);
    }

    @PutMapping("/update")
    public Integer updateArticle(@RequestBody ArticleCommand articleCommand) {
        if (Objects.isNull(articleCommand)) {
            throw new AssertionException(500004, "上传失败");
        }
        return articleService.updateArticle(articleCommand);
    }

}
