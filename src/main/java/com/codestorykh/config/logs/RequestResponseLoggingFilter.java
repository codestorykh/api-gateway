package com.codestorykh.config.logs;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RequestResponseLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        // Log the incoming request
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Request: {} {} with headers: {}", request.getMethod(), request.getURI(), request.getHeaders());

        // Add a response filter to capture the response data
        ServerHttpResponse response = exchange.getResponse();
        return chain.filter(exchange).doOnTerminate(() -> {
            // Log the response status
            logger.info("Response status: {}", response.getStatusCode());
        });
    }

    @Override
    public int getOrder() {
        return -1;  // Set the order of the filter (priority)
    }
}
