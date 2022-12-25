package com.huhuhux;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.annotation.ApplicationScope;

@SpringBootApplication
@MapperScan("com.huhuhux.mapper")
@EnableScheduling
public class HuhuhuxApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuhuhuxApplication.class,args);
    }
}
