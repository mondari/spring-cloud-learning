# spring-cloud-learning

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

**各服务端口**
服务网关端口：8080
注册中心端口：8761(eureka)，8848(nacos)
配置中心端口：8888
admin端口：8769
