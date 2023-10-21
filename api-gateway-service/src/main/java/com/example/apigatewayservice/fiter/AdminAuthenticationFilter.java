package com.example.apigatewayservice.fiter;

import com.example.apigatewayservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationFilter extends AbstractGatewayFilterFactory<AdminAuthenticationFilter> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private TokenUtil tokenUtil;
    @Override
    public GatewayFilter apply(AdminAuthenticationFilter config) {
        return (((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())) {
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Auth token missing");
                }
                String authToken = tokenUtil.extractAuthToken(
                        exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
                if(!tokenUtil.validateTokenForRole(authToken, "ADMIN")) {
                    throw new RuntimeException("Only Admin can access this action");
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
