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
public class CommonParamsConfig
{
    // CommonParams
    @Value("${institution-type}")
    private String institutionType;
    @Value("${institution-code}")
    private String institutionCode;


    @Value("${com-path}")
    private String comPath;
    @Value("${version}")
    private String version;

}
