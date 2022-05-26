package com.codezl.ex_three.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacos-provider")
public interface ProviderFeign {

    @GetMapping("/invoke")
    String invoke();
}
