package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class GatewayApplication {

    public static final String URI_HTTP_HTTPBIN_ORG = "http://httpbin.org";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route("path_route", r -> r
                        .path("/get")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.addRequestHeader("Hello", "World"))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("host_route", r -> r
                        .host("*.myhost.com")
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("hystrix", r -> r
                        .host("*.hystrix.com")
                        .filters(f -> f.hystrix(config -> config.setName("mycmd")))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("hystrix_fallback", r -> r
                        .host("*.hystrixfallback.com")
                        .filters(f -> f.hystrix(config -> config
                                .setName("slowcmd")
                                .setFallbackUri("forward:/hystrixfallback")))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("rewrite_route", r -> r
                        .host("*.rewrite.com")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)",
                                "/${segment}"))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                // 使用令牌桶（底层基于redis）进行限流配置（可以放到配置文件中进行配置）
                .route("limit_route", r -> r
                        .host("*.limited.com").and().path("/anything/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("websocket_route", r -> r
                        .path("/echo")
                        .uri("ws://localhost:9000"))
                .build();
        //@formatter:on
    }

    @GetMapping("/hystrixfallback")
    public Mono<String> hystrixfallback() {
        return Mono.just("This is a fallback");
    }

    /**
     * ReplenishRate: 令牌桶填充速率
     * BurstCapacity：令牌桶容量
     * @return
     */
    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.httpBasic().and()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/anything/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .build();
    }

    /**
     * 相当于配置文件中的 spring.security.user
     *
     * @return
     */
    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }
}
