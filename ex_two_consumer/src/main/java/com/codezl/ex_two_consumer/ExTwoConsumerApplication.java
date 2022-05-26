package com.codezl.ex_two_consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScans(value = {@ComponentScan("com.codezl")})
@MapperScan("com.codezl.ex_two_consumer.mapper")
public class ExTwoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExTwoConsumerApplication.class, args);
    }

}
