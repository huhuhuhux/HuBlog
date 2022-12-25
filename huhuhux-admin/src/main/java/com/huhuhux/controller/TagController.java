package com.huhuhux.controller;

import com.huhuhux.doman.ResponseResult;
import com.huhuhux.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("list")
    public ResponseResult tagList(){
        return ResponseResult.okResult(tagService.list());
    }
}
