package com.huhuhux.job;

import com.huhuhux.doman.entity.Article;
import com.huhuhux.service.ArticleService;
import com.huhuhux.util.RedisCache;
import io.swagger.models.auth.In;
import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;



    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //获取redis里的阅读量
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewcount");

        //封装为list
        List<Article> articles = cacheMap.entrySet().stream()
                .map(entry -> new Article(Long.getLong(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //存入数据库
        articleService.updateBatchById(articles);

    }
}
