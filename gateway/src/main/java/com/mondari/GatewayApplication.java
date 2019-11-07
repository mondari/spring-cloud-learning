package com.mondari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableWebSecurity
@SpringBootApplication
public class GatewayApplication {

    public static final String URI_HTTP_HTTPBIN_ORG = "http://httpbin.org";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @RequestMapping("/hystrixfallback")
    public String hystrixfallback() {
        return "This is a fallback";
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route("path_route", r -> r.path("/get")
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("host_route", r -> r.host("*.myhost.org")
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("rewrite_route", r -> r.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)",
                                "/${segment}"))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("hystrix_route", r -> r.host("*.hystrix.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("limit_route", r -> r
                        .host("*.limited.org").and().path("/anything/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri(URI_HTTP_HTTPBIN_ORG))
                .route("websocket_route", r -> r.path("/echo")
                        .uri("ws://localhost:9000"))
                .build();
        //@formatter:on
    }

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

    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }
}
