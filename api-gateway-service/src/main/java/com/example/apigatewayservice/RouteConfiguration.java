package com.example.apigatewayservice;

import com.example.apigatewayservice.fiter.AdminAuthenticationFilter;
import com.example.apigatewayservice.fiter.UserAuthenticationFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator getRouteLocator(UserAuthenticationFilter userAuthenticationFilter,
                                        RouteLocatorBuilder routeLocatorBuilder) {
        UserAuthenticationFilter.Config config = new UserAuthenticationFilter.Config();
        GatewayFilter userFilter = userAuthenticationFilter.apply(config);
        return routeLocatorBuilder.routes()
                .route(p ->p.path("/user/**")
                        .uri("lb://user-service"))
                .route(p ->p.path("/product/**")
                        .uri("lb://product-service"))
                .route(p ->p.path("/order/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(userFilter))
                        .uri("lb://order-service")).build();
    }
}
