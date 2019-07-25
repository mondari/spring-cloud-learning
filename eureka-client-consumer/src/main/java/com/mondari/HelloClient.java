package com.mondari;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-producer")
public interface HelloClient {

    /**
     * 这里要与服务生产者的接口定义一致
     *
     * @return
     */
    @GetMapping("/hello")
    String hello(@RequestParam(defaultValue = "world", required = false) String name);

}
