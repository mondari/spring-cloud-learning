package com.mondari;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-producer", fallbackFactory = HelloClient.HelloClientFallbackFactory.class)
public interface HelloClient {

    /**
     * 这里要与服务生产者的接口定义一致
     *
     * @return
     */
    @GetMapping("/hello")
    String hello(@RequestParam(defaultValue = "world", required = false) String name);

    /**
     * Hystrix Fallback
     * <p>
     * FeignClient注解添加属性 fallback = HelloClient.Fallback.class
     */
//    @Component
//    class HelloClientFallback implements HelloClient {
//        @Override
//        public String hello(String name) {
//            return "404";
//        }
//    }

    /**
     * Hystrix Fallback with clause
     * <p>
     * FeignClient注解添加属性 fallbackFactory = HelloClient.HelloClientFallbackFactory.class
     *
     * 怎么看Hystrix Dashboard？
     * 打开 http://localhost:8080/hystrix，输入 http://localhost:8080/actuator/hystrix.stream，点击monitor按钮即可
     *
     */
    @Component
    class HelloClientFallbackFactory implements FallbackFactory<HelloClient> {

        @Override
        public HelloClient create(Throwable throwable) {
            return new HelloClient() {
                @Override
                public String hello(String name) {
                    return "404, reason is " + throwable.getMessage();
                }
            };
        }
    }
}
