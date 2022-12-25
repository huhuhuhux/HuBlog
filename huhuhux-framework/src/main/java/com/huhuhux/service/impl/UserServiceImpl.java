package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.User;
import com.huhuhux.doman.enums.AppHttpCodeEnum;
import com.huhuhux.doman.vo.UserInfoVo;

import com.huhuhux.exception.SystemException;
import com.huhuhux.mapper.UserMapper;
import com.huhuhux.service.UserService;
import com.huhuhux.util.BeanCopyUtils;
import com.huhuhux.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;

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

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);

        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userRegister(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        String endPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(endPassword);

        save(user);

        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        int count = count(queryWrapper);

            return count>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        int count = count(queryWrapper);
            return count>0;
    }
}

