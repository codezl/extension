package com.codezl.ex_two.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RefreshScope
public class ProviderController {

    @Autowired
    ConfigurableApplicationContext context;
    @Value("${devConfig}")
    String cofig;
//    @Value("${properties.nnn}")
//    String config_2;
    @Value("${config.name}")
    String config_2;

    @GetMapping("invoke")

    public String invoke() {
        System.out.println("调用feign");
        System.out.println(context.getEnvironment().getProperty("devConfig"));
        return LocalTime.now() + "invoke>>>"+"config_1>>>"+cofig+"config_2>>>"+config_2;

    }

}