package com.codezl.ex_three.controller;

import com.alibaba.fastjson.JSONObject;
import com.codezl.ex_three.feign.OrderFeign;
import com.codezl.ex_three.feign.ProviderFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("t")
public class TestController {

    @Resource
    ProviderFeign providerFeign;
    @Resource
    OrderFeign orderFeign;
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefault")
    @GetMapping("invoke")
    public String invoke() {
        String invoke = providerFeign.invoke();
        return invoke;
    }

    @GetMapping("countPrice")
    public String count() {
        JSONObject jsonObject = orderFeign.countPrice();
        System.out.println("调用计价feign>>>"+jsonObject);
        return "";
    }

    @GetMapping("hyx")
    public String hyx() {

        JSONObject jsonObject = orderFeign.countPrice();
        System.out.println("调用计价feign>>>"+jsonObject);
        return "";
    }

    public String getDefault() {
        System.out.println("请求失败，转到默认值");
        String distance = "1000";
        ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity("lb://ORDER/fixPrice/countPrice" + distance, JSONObject.class);
        System.out.println("转到计价>>>"+forEntity);
        return "请求失败，转到默认值";
    }
}
