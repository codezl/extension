package com.codezl.ex_three.Impl;

import com.alibaba.fastjson.JSONObject;
import com.codezl.ex_three.feign.OrderFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ll
 */
@Slf4j
@Component
public class OrderFeignImpl implements OrderFeign {
    @Override
    public JSONObject countPrice() {
        System.out.println("进入计价熔断");
        return null;
    }
}
