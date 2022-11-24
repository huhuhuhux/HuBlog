package com.huhuhux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Article;
import org.springframework.stereotype.Service;


public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticle(Long id);
}
