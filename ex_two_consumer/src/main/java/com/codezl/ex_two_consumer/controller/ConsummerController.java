package com.codezl.ex_two_consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.codezl.ex_two_consumer.feign.ProviderFeign;
import com.codezl.ex_two_consumer.mapper.TestSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsummerController {

    @Autowired
    private ProviderFeign providerFeign;

    @Autowired
    TestSqlMapper sqlMapper;

    @GetMapping("/test")
    public String test() {
        return providerFeign.invoke();
    }

    @GetMapping("find")
    public List<JSONObject> find() {
        return sqlMapper.findUsers();
    }
}