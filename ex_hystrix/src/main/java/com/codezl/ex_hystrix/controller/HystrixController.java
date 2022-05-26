package com.codezl.ex_hystrix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hystrix")
public class HystrixController {

    @RequestMapping("one")
    public void one() {
        System.out.println("hystrix》》》one");
    }

    @RequestMapping
    public void defaultMethod() {
        System.out.println("执行"+this.getClass().getName());
    }
}
