package com.example.config;

import com.example.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport
{
//    /**
//     * 该类为了扫描 resources-backend 和 front的静态资源映射
//     * 并且原有的static访问路径失效
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry)
//    {
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
//    }

    /**
     * 扩展消息转换器 与common.JacksonObjectMapper一起使用
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用Jackson将java转为json
        // 加入自己的消息转换器 JacksonObjectMapper
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将该转换器加入mvc框架的转换器集合中，0代表优先级，0为最高
        converters.add(0,messageConverter);
    }
}
