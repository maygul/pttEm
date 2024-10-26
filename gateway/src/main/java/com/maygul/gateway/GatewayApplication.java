package com.maygul.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    @Value("${spring.eureka.client.serviceUrl.defaultZone}")
    private String value;

    public static void main(String[] args) {


        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("eureka-url-aq: {}", value);
    }
}
