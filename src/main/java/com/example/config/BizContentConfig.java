package com.example.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/bestpay.yml")
//@ConfigurationProperties(prefix = "bpay") //读取wxpay节点
@Data
@Slf4j
public class BizContentConfig
{
    //biz-content
    @Value("${merchant-no}")
    private String merchantNo;
    @Value("${operator}")
    private String operator;
    @Value("${notify-url}")
    private String notifyUrl;
    @Value("${access-code}")
    private String accessCode;
    @Value("${ccy}")
    private String ccy;
    @Value("${medium-type}")
    private String mediumType;
    @Value("${trade-channel}")
    private String tradeChannel;
}
