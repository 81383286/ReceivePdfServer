package com.jiashi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//该注解会扫描相应的包
@MapperScan("com.jiashi.dao")
public class Application {
    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(Application .class);
        springApplication.run(args);
    }
}
