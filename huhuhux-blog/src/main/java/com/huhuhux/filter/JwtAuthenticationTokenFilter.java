package com.huhuhux.filter;

import com.alibaba.fastjson.JSON;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.LoginUser;
import com.huhuhux.doman.enums.AppHttpCodeEnum;
import com.huhuhux.util.JwtUtil;
import com.huhuhux.util.RedisCache;
import com.huhuhux.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.netty.util.internal.StringUtil;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private RedisCache redisCache;
    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头
        String token = httpServletRequest.getHeader("token");

        if (StringUtil.isNullOrEmpty(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String userId ;
        //解析token
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId= claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        String key = "login"+userId;
        LoginUser cacheObject = redisCache.getCacheObject(key);

        //存入到ContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(cacheObject,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
