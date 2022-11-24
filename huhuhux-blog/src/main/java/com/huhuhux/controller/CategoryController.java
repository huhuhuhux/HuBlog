package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2022-11-08 20:02:48
 */
@RestController
@RequestMapping("/category")
public class  CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult selectCategory(){

        return categoryService.selectCategory();
    }
}

