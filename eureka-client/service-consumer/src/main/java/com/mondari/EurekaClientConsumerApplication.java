package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientConsumerApplication.class, args);
    }

    @RestController
    class HelloController {

        final
        HelloClient helloClient;

        public HelloController(HelloClient helloClient) {
            this.helloClient = helloClient;
        }

        /**
         * 浏览器访问 http://localhost:{port}/hello
         * <p>
         * 两种访问结果“Hello world!(from master producer)” 和 “Hello world!(from slave producer)” 会交替出现
         * 这说明客户端会交替访问同一服务的生产者，也就是说客户端负载均衡（通过Ribbon实现）生效
         *
         * @param name
         * @return
         */
        @GetMapping("hello")
        public String hello(@RequestParam(defaultValue = "world", required = false) String name) {
            return helloClient.hello(name);
        }
    }


}
