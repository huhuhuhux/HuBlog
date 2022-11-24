package com.huhuhux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Category;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-15 14:48:06
 */
public interface CategoryService extends IService<Category> {

    ResponseResult selectCategory();
}

