package com.codezl.ex_two_consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacos-provider")
public interface ProviderFeign {
    @GetMapping("invoke")
    String invoke();

}