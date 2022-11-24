package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Article;
import com.huhuhux.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    public List<Article> selectAll(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    //因为请求是qurey类型,所以不需要加注解,直接请求
    //qiery类型就是请求后面加上?pageNum=12&pageSize=23
    public ResponseResult ArticleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable("id") Long id ){
        return articleService.getArticle(id);
    }
}
