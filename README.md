# spring-cloud-learning

Spring Cloud 各组件的使用示例

## 项目介绍

[eureka-server](./eureka-server/README.md)：注册中心（Netflix Eureka），端口是8761

[eureka-client](./eureka-client/README.md)/service-producer, service-consumer：服务生产者和消费者（Netflix Eureka），端口分别是8081和8082

[zuul](./zuul/README.md)：服务网关（Netflix Zuul），端口是8080

[gateway](./gateway/README.md)：服务网关（Spring Cloud Gateway），端口是8080

[config](./config/README.md)：配置中心（Spring Cloud Config），端口是8888

[vault](./vault/README.md)：配置加密管理（Spring Cloud Vault），端口是

[admin](./admin/README.md)/admin-server, admin-client：服务管理界面（Spring Boot Admin），端口是8769

## 启动项目

1. 先启动注册中心 eureka-server
2. 再启动配置中心 config
3. 启动 service-producer、service-consumer、zuul 或 gateway（二选其一）、admin
4. 访问注册中心 http://localhost:8761，服务生产者 http://localhost:8081/hello，服务消费者 http://localhost:8082/hello，服务网关 http://localhost:8080，服务管理 http://localhost:8769

## Spring Cloud 组件
注册中心（Service Discovery）： Netflix Eureka，HashiCorp Consul， Zookeeper，etcd，nacos

客户端负载均衡（Load Balancer）： Netflix Ribbon，Spring Cloud Feign

断路器，熔断（Circuit Breaker）：Netflix Hystrix，Alibaba Sentinel

声明式服务调用：Feign（本质上就是Ribbon+Hystrix）

配置中心（External Configuration）：Spring Cloud Config，Ctrix Apollo，disconf

配置加密：HashiCorp Vault

服务网关（Router） ：Netflix Zuul，Spring Cloud Gateway，Kong

测试（Test）：Spring Cloud Contract

链路追踪（Trace）：Spring Cloud Sleuth

消息总线（Message Broker 消息代理）：Spring Cloud Bus

参考：https://blog.csdn.net/yejingtao703/article/details/78331442

bootstrap.yml 中需要配置好应用名称、注册中心和配置中心
