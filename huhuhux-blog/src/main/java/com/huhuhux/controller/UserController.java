package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.User;
import com.huhuhux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody User user){
        return userService.userRegister(user);
    }

}
