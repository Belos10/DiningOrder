package com.example;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigInteger;
import java.util.HashMap;


@SpringBootApplication
@Slf4j
@ServletComponentScan   // 用来扫描@WebFilter等servlet注解
@EnableTransactionManagement
@EnableCaching          // 开启缓存注解
public class TakeoutApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TakeoutApplication.class,args);
        System.out.println("localhost:8080/backend/page/login/login.html");
        System.out.println("localhost:8080/front/page/login.html");
    }
}