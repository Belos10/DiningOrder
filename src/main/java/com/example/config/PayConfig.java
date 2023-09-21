package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ConfigurationProperties(prefix = "bestpay")
@PropertySource("classpath:config/bestpay.yml")
@Data
public class PayConfig
{
    private String userCertificateName;
    private String serviceCertificateName;
    private String passwd;
    private String alias;
    private String keyStoreType;
}
