package com.huhuhux.service.impl;

import com.google.gson.Gson;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.enums.AppHttpCodeEnum;
import com.huhuhux.exception.SystemException;
import com.huhuhux.service.UploadService;
import com.huhuhux.util.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Data
@Service
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey ;
    private String bucket;
    private String url;

    @Override
    public ResponseResult upload(MultipartFile img) {

        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = upload2Qiniu(img, filePath);

        return ResponseResult.okResult(url);
    }

    public String upload2Qiniu(MultipartFile multipartFile, String filePath){
        //构造一个带指定Zone对象的配置类

        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        String key = filePath;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            byte[] bytes = multipartFile.getBytes();
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return url+key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
