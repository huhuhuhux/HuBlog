package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.constants.SystemConstants;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Article;
import com.huhuhux.doman.entity.Category;
import com.huhuhux.doman.vo.CategoryVo;
import com.huhuhux.mapper.ArticleMapper;
import com.huhuhux.mapper.CategoryMapper;
import com.huhuhux.service.ArticleService;
import com.huhuhux.service.CategoryService;
import com.huhuhux.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-15 14:48:06
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    ArticleService articleService;


    @Override
    public ResponseResult selectCategory() {

        LambdaQueryWrapper<Article> queryWrapperArticle = new LambdaQueryWrapper<>();
        queryWrapperArticle.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(queryWrapperArticle);

        List<Long> articleCategoryIds = articles.stream()
                .map(article -> article.getCategoryId())
                .distinct()
                .collect(Collectors.toList());

        List<Category> categories = listByIds(articleCategoryIds);
        categories =categories.stream()
                .filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.beanCopyList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}

