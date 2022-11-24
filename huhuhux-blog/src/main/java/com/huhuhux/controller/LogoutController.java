package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogoutController {

    @Autowired
    LoginUserService loginUserService;

    @PostMapping("logout")
    public ResponseResult logout(){
        return loginUserService.logout();
    }
}
