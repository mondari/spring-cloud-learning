package com.mondari;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientProducerApplication.class, args);
    }

    @Value("${hello:Hello %s!(default)}")
    String hello;

    @RestController
    class ServiceProviderRestController {

        private final DiscoveryClient discoveryClient;

        public ServiceProviderRestController(DiscoveryClient discoveryClient) {
            this.discoveryClient = discoveryClient;
        }

        /**
         * 浏览器访问 http://localhost:{port}/service-instances/service-producer
         *
         * @param applicationName
         * @return
         */
        @GetMapping("/service-instances/{applicationName}")
        public List<ServiceInstance> serviceInstancesByApplicationName(
                @PathVariable String applicationName) {
            List<ServiceInstance> instances = this.discoveryClient.getInstances(applicationName);
            if (!CollectionUtils.isEmpty(instances)) {
                return instances;
            }
            return new ArrayList<>();
        }

        /**
         * 浏览器访问 http://localhost:8090/hello
         *
         * @return
         */
        @GetMapping("/hello")
        public String hello(@RequestParam(defaultValue = "world", required = false) String name) {
            return String.format(hello, name);
        }
    }
}
