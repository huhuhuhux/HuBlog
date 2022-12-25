package com.huhuhux.runner;

import com.huhuhux.doman.entity.Article;
import com.huhuhux.service.ArticleService;
import com.huhuhux.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        List<Article> articles = articleService.list();

        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));

        redisCache.setCacheMap("article:viewcount",collect);

    }
}
