package com.huhuhux.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.constants.SystemConstants;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Article;
import com.huhuhux.doman.entity.Category;
import com.huhuhux.doman.vo.ArticleDetailVo;
import com.huhuhux.doman.vo.ArticleListVo;
import com.huhuhux.doman.vo.PageVo;
import com.huhuhux.mapper.ArticleMapper;
import com.huhuhux.service.ArticleService;
import com.huhuhux.service.CategoryService;
import com.huhuhux.util.BeanCopyUtils;
import com.huhuhux.doman.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //查询十篇
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //封装


        //用bean拷贝使传给前端vo形式
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article:articles) {
//            HotArticleVo articleVo = new HotArticleVo();
//            BeanUtils.copyProperties(article,articleVo);
//            articleVos.add(articleVo);
//        }

        List<HotArticleVo> articleVos = BeanCopyUtils.beanCopyList(articles, HotArticleVo.class);

        System.out.println(articleVos);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper();
        //如果有categoryId就要查询和传入相同的
        articleWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //分页

        Page<Article> page = new Page(pageNum,pageSize);

        page(page, articleWrapper);

        List<Article> articles = page.getRecords();

        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());


        List<ArticleListVo> articleListVos = BeanCopyUtils.beanCopyList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticle(Long id) {
        Article article = getById(id);

        ArticleDetailVo articleDetailVo = BeanCopyUtils.beanCopy(article, ArticleDetailVo.class);

        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category!=null) {
            articleDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailVo);
    }
}
