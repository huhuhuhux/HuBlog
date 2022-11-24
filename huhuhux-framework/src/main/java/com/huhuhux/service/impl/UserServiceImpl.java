package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.doman.entity.User;
import com.huhuhux.mapper.UserMapper;
import com.huhuhux.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-11-23 14:29:46
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

