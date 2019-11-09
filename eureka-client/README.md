# eureka-client

主要功能：
- Ribbon 客户端负载均衡
- Feign 声明式调用
- Hystrix 熔断
- Hystrix Stream 和 Hystrix Dashboard
- Sleuth 和 Zipkin 分布式链路追踪

## 如何开启 Hystrix Dashboard
1. 添加依赖：`spring-cloud-starter-netflix-hystrix` `spring-cloud-starter-netflix-hystrix-dashboard` `spring-boot-starter-actuator`
2. 添加配置：management.endpoints.web.exposure.include: hystrix.stream
3. 添加注解：`@EnableHystrix` `@EnableHystrixDashboard`
4. 浏览器打开 http://localhost:8080/hystrix，输入 http://localhost:8080/actuator/hystrix.stream，点击 `monitor` 按钮即可

## 如何启动 Zipkin
打开工程 lib 目录，执行 `java -jar zipkin-server-2.19.1-exec.jar ` ，启动注册中心和 eureka-client 服务