package com.example.apigatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p ->p.path("/user/**")
                        .uri("lb://user-service"))
                .route(p ->p.path("/product/**")
                        .uri("lb://product-service")).build();
    }
}
