package com.huhuhux.service.impl;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.LoginUser;
import com.huhuhux.doman.entity.User;
import com.huhuhux.doman.vo.UserInfoVo;
import com.huhuhux.doman.vo.UserVo;
import com.huhuhux.service.LoginUserService;
import com.huhuhux.util.BeanCopyUtils;
import com.huhuhux.util.JwtUtil;
import com.huhuhux.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.util.Objects;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取loginUser
        LoginUser loginUser= (LoginUser) authenticate.getPrincipal();

        //将loginUser的id封装成jwt
        String username = loginUser.getUser().getUserName().toString();
        String token = JwtUtil.createJWT(username);
        //将loginUser存入redis
        redisCache.setCacheObject("bolgLogin"+username,loginUser);

        //封装
        UserInfoVo userInfoVo = BeanCopyUtils.beanCopy(loginUser.getUser(), UserInfoVo.class);

        ResponseResult responseResult = ResponseResult.okResult(new UserVo(token, userInfoVo));

        return responseResult;
    }

    @Override
    public ResponseResult logout() {
        //获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userName = loginUser.getUser().getUserName();
        //删除redis的用户信息
        redisCache.deleteObject("bolgLogin"+userName);
        //返回
        return ResponseResult.okResult();
    }
}
