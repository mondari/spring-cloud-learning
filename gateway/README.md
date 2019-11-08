# gateway

## 请求示例

```bash
$ curl http://localhost:8080/get
{
  "args": {},
  "headers": {
    "Accept": "*/*",
    "Forwarded": "proto=http;host=\"localhost:8080\";for=\"0:0:0:0:0:0:0:1:57657\"",
    "Hello": "World",
    "Host": "httpbin.org",
    "User-Agent": "curl/7.55.1",
    "X-Forwarded-Host": "localhost:8080"
  },
  "origin": "0:0:0:0:0:0:0:1, 219.137.191.128, ::1",
  "url": "https://localhost:8080/get"
}
```

gateway 将请求转发到了 httpbin，并在 header 中添加 `"Hello": "World"`。



```bash
$ curl --dump-header - --header 'Host: www.hystrix.com' http://localhost:8080/delay/3

HTTP/1.1 504 Gateway Timeout

$ curl --dump-header - --header 'Host: www.hystrixfallbackcom' http://localhost:8080/delay/3

HTTP/1.1 200 OK
This is a fallback
```

> We are using `--dump-header` to see the response headers, the `-` after `--dump-header` is telling cURL to print the headers to stdout. 

第一个请求由于 gateway 中没有指明 fallback 方法，所以返回 504；第二个请求返回 200 以及 fallback 方法的内容 “This is a fallback”。

## Websocket 示例

[安装 wscat](https://www.npmjs.com/package/wscat)

运行 websocket 服务端:

```
wscat --listen 9000
```

运行 websocket 客户端, 通过 gateway 进行连接:

```
wscat --connect ws://localhost:8080/echo
```

type away in either server and client, messages will be passed appropriately.

## Redis Rate Limiter 限流测试

保证本地 redis 服务启动，地址为 localhost:6379

运行 `DemogatewayApplicationTests` 中的单元测试，会返回 29 TO_MANY_REQUESTS HTTP status.

## 踩坑记录

### No qualifying bean of type 'org.springframework.core.convert.ConversionService' available 异常

具体错误信息：
```bash
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.core.convert.ConversionService' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Qualifier(value=webFluxConversionService)}
```

这是因为 spring-cloud-starter-gateway 已经包含 spring-boot-starter-webflux 依赖，如果引进 spring-boot-starter-web 依赖的话，就会出现上面异常。
解决方法是去掉 spring-boot-starter-web 依赖。

## 参考

https://github.com/spring-cloud/spring-cloud-gateway/tree/master/spring-cloud-gateway-sample

https://github.com/spring-cloud-samples/spring-cloud-gateway-sample

https://spring.io/guides/gs/gateway/