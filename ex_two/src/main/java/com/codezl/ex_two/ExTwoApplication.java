package com.codezl.ex_two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ExTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExTwoApplication.class, args);
    }

}
