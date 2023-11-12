package com.example.springstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springstudy.mapper")
public class SpringStudyApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }

}
