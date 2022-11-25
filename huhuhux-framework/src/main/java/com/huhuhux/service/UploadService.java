package com.huhuhux.service;


import com.huhuhux.doman.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult upload(MultipartFile multipartFile);
}
