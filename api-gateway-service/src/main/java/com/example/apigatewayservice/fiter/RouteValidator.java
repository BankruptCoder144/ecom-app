package com.example.apigatewayservice.fiter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = new ArrayList<String>() {{
        add("/user/register");
        add("/user/admin/register");
        add("/user/token");
        add("/eureka");
    }};

    public Predicate<ServerHttpRequest> isSecured = request ->
            openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
