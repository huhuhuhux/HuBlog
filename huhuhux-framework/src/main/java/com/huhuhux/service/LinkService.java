package com.huhuhux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Link;


/**
 * &#x53CB;&#x94FE;(Link)&#x8868;&#x670D;&#x52A1;&#x63A5;&#x53E3;
 *
 * @author makejava
 * @since 2022-11-10 15:10:50
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

