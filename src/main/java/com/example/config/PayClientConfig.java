package com.example.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.bestpay.api.BestpayClient;
import com.bestpay.api.DefaultBestpayClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:config/bestpay.yml")
//@ConfigurationProperties(prefix = "bpay") //读取wxpay节点
@Data
@Slf4j
public class PayClientConfig
{
    // Client
    @Value("${user-certificate-name}")
    private String userCertificateName;
    @Value("${service-certificate-name}")
    private String serviceCertificateName;
    @Value("${passwd}")
    private String passwd;
    @Value("${alias}")
    private String alias;
    @Value("${key-store-type}")
    private String keyStoreType;



    @Bean
    public BestpayClient bestpayClient() throws AlipayApiException
    {
        //用户证书名称
        String userCertificateName = getUserCertificateName();
        //服务证书名称
        String serviceCertificateName = getServiceCertificateName();
        String passwd = getPasswd();
        //别名
        String alias = getAlias();
        //秘钥存储类型
        String keyStoreType = getKeyStoreType();
        //创建SDK客户端
        BestpayClient bestpayClient = new DefaultBestpayClient(userCertificateName, serviceCertificateName,
                passwd, alias, keyStoreType);

        return bestpayClient;
    }
}
