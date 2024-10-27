package com.maygul.order.config;

import com.maygul.order.logger.RestTemplateLoggingInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonBeanConfig {

    @LoadBalanced
    @Bean(name = "loggedRestTemplate")
    public RestTemplate loggedRestTemplate(){
        return new RestTemplateBuilder()
                .interceptors(new RestTemplateLoggingInterceptor())
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/messages");
        messageSource.setCacheSeconds(60 * 60 * 24 * 365);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
