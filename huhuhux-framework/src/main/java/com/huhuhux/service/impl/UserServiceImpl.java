package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.User;
import com.huhuhux.doman.vo.UserInfoVo;

import com.huhuhux.mapper.UserMapper;
import com.huhuhux.service.UserService;
import com.huhuhux.util.BeanCopyUtils;
import com.huhuhux.util.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-11-23 14:29:46
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {

        Long userId = SecurityUtils.getUserId();

        User user = getById(userId);

        UserInfoVo userVo = BeanCopyUtils.beanCopy(user,UserInfoVo.class);

        return ResponseResult.okResult(userVo);
    }
}

