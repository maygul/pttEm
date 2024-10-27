package com.maygul.order.config;

import com.maygul.order.interceptor.RestTemplateLoggingInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonBeanConfig {

    @Bean(name = "loggedRestTemplate")
    public RestTemplate loggedRestTemplate(){
        return new RestTemplateBuilder()
                .interceptors(new RestTemplateLoggingInterceptor())
                .build();
    }
}
