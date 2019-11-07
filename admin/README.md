# admin

- admin-server: 集成 spring.boot.admin、服务注册、spring security 对 admin 进行登录认证和对 actuator 进行登录认证、hazelcast 搭建 admin 集群
- admin-client: 集成 spring.boot.admin、服务注册、spring security 对 actuator 进行登录认证

推荐将 spring.boot.admin 集成在注册中心中，减少服务的数量并保证高可用。但需要添加 spring.boot.admin.context-path 防止admin和eureka的web页面冲突

