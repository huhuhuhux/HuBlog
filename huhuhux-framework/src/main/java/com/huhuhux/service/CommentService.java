package com.huhuhux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-23 13:07:30
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum,Integer pageSize);

    ResponseResult addComment(Comment comment);
}

