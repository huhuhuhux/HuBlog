package com.huhuhux.service;


import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.User;

public interface LoginUserService {

    ResponseResult login(User user);

    ResponseResult logout();
}
