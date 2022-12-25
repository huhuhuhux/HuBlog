package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.constants.SystemConstants;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Comment;
import com.huhuhux.doman.entity.User;
import com.huhuhux.doman.enums.AppHttpCodeEnum;
import com.huhuhux.doman.vo.CommentVo;
import com.huhuhux.doman.vo.PageVo;
import com.huhuhux.exception.SystemException;
import com.huhuhux.mapper.CommentMapper;
import com.huhuhux.service.CommentService;
import com.huhuhux.service.UserService;
import com.huhuhux.util.BeanCopyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-23 13:07:31
 */
@Service("commentService")
public  class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //分页查出该文章的有根评论
        LambdaQueryWrapper<Comment> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.eq(SystemConstants.COMMENT_TYPE_ARTICLE.equals(commentType),Comment::getArticleId,articleId);

        queryWrapper.eq(Comment::getRootId,-1);

        queryWrapper.eq(Comment::getType,commentType);

        Page page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        if (Objects.nonNull(page.getRecords())) {


            List<CommentVo> commentVos = toCommentVoList(page.getRecords());
            commentVos = commentVos.stream()
                    .map(commentVo -> {
                        List<CommentVo> children = getChildren(commentVo.getId());
                        commentVo.setChildren(children);
                        return commentVo;
                    })
                    .collect(Collectors.toList());


            return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
        }
        return null;

    }

    @Override
    public ResponseResult addComment(Comment comment) {

        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByDesc(Comment::getToCommentId);

        List<Comment> list = list(queryWrapper);
        if (Objects.nonNull(list)){
            List<CommentVo> commentVos = toCommentVoList(list);
            return commentVos;
        }
        return null;
    }


    private List<CommentVo> toCommentVoList(List<Comment> comments){

        List<CommentVo> commentVos = BeanCopyUtils.beanCopyList(comments, CommentVo.class);

        commentVos= commentVos
                .stream()
                //将username遍历查询封装到vo中
                .map(commentVo -> commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getUserName()))
                .map(commentVo -> {
                    if (commentVo.getToCommentId()!=-1){
                        Long id = commentVo.getToCommentUserId();
                        User user = userService.getById(id);
                        commentVo.setToCommentUserName(user.getUserName());
                    }else {
                        commentVo.setToCommentUserName("-1");
                    }
                    return commentVo;
                })
                .collect(Collectors.toList());

        return commentVos;
    }
}

