## eureka-client-consumer
eureka客户端，同时也是服务的消费者，消费eureka-client-producer的服务

### 如何开启 Hystrix Dashboard
1. 添加依赖：`spring-cloud-starter-netflix-hystrix` `spring-cloud-starter-netflix-hystrix-dashboard` `spring-boot-starter-actuator`
2. 添加配置：management.endpoints.web.exposure.include: hystrix.stream
3. 添加注解：`@EnableHystrix` `@EnableHystrixDashboard`
4. 浏览器打开 http://localhost:8080/hystrix，输入 http://localhost:8080/actuator/hystrix.stream，点击 `monitor` 按钮即可

