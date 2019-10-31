# spring-cloud-learning

## Spring Cloud 组件
注册中心（Service Discovery）： Netflix Eureka， Consul， Zookeeper，etcd

客户端负载均衡（Load Balancer）： Netflix Ribbon，Spring Cloud Feign

断路器，熔断（Circuit Breaker）：Netflix Hystrix，Alibaba Sentinel

声明式服务调用：Feign（本质上就是Ribbon+Hystrix）

配置中心（External Configuration）：Spring Cloud Config，Ctrix Apollo，disconf

配置加密：Spring Cloud Vault

服务网关（Router） ：Netflix Zuul，Spring Cloud Gateway，Kong

测试（Test）：Spring Cloud Contract

链路追踪（Trace）：Spring Cloud Sleuth

消息总线（Message Broker 消息代理）：Spring Cloud Bus

参考：https://blog.csdn.net/yejingtao703/article/details/78331442

## 项目介绍
1. eureka-server： 单节点和多节点注册中心
2. eureka-client-producer-xxx：eureka客户端，同时也是服务生产者
3. eureka-client-consumer：eureka客户端，同时也是服务消费者。通过Feign消费eureka-client-producer-xxx的服务，
通过Ribbon实现客户端负载均衡，开启了Feign Hystrix断路器，开启了Hystrix Dashboard
4. 

