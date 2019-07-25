# spring-cloud-learning

## Spring Cloud 组件
注册中心（Service Discovery）： Eureka， Consul， Zookeeper，etcd

客户端负载均衡（Load Balancer）： Ribbon，Feign

断路器（Circuit Breaker）：Hystrix

配置中心（External Configuration）：Spring Cloud Config，Apollo

配置加密：Spring Cloud Vault

服务网关（Router） ：Zuul，Spring Cloud Gateway

测试（Test）：Spring Cloud Contract

链路追踪（Trace）：Sleuth

消息总线：Spring Cloud Bus

## 项目介绍
1. eureka-server： 单节点和多节点注册中心
2. eureka-client-producer-xxx：eureka客户端，同时也是服务生产者
3. eureka-client-consumer：eureka客户端，同时也是服务消费者，通过Feign消费eureka-client-producer-xxx的服务，并通过Ribbon实现客户端负载均衡
4. 

