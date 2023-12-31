package com.example.apigatewayservice.fiter;

import com.example.apigatewayservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserAuthenticationFilter extends AbstractGatewayFilterFactory<UserAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private TokenUtil tokenUtil;
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())) {
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Auth token missing");
                }
                String authToken = tokenUtil.extractAuthToken(
                        exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
                if(!tokenUtil.validateTokenForRole(authToken, "USER")) {
                    return Mono.error(new RuntimeException("Only user can perform this action1"));
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
