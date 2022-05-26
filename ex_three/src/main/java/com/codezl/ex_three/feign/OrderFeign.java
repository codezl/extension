package com.codezl.ex_three.feign;

import com.alibaba.fastjson.JSONObject;
import com.codezl.ex_three.Impl.OrderFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order",fallback = OrderFeignImpl.class)
public interface OrderFeign {

    @GetMapping("fixPrice/countPrice")
    JSONObject countPrice();
}
