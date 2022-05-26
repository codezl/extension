package com.codezl.ex_one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.codezl")
public class ExOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExOneApplication.class, args);
    }

}
