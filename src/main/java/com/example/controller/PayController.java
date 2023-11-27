package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/pay")
public class PayController
{
    //异步通知支付结果
    @PostMapping("/notify")
    public void tradeNotify(@RequestParam Map<String, String> params){
//        log.info("异步通知支付结果");
//        log.info("异步通知参数params" + params);
//        String result = "failure";
//        try{
//            //异步通知验签
//
//        }
//        //验签成功
//        log.info("验签成功");

    }
}
