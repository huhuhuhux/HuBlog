package com.huhuhux.controller;

import com.huhuhux.constants.SystemConstants;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Comment;
import com.huhuhux.service.CommentService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum,Integer pageSize){

        return commentService.commentList(SystemConstants.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("linkCommentList")
    public ResponseResult commentLinkList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.COMMENT_TYPE_LINK,null,pageNum,pageSize);
    }

}
