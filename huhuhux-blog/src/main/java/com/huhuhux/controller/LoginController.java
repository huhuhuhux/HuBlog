package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.User;
import com.huhuhux.doman.enums.AppHttpCodeEnum;
import com.huhuhux.exception.SystemException;
import com.huhuhux.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginUserService loginUserService;

    @PostMapping("/login")
    public ResponseResult loginUser(@RequestBody User user){

        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginUserService.login(user);
    }
}
