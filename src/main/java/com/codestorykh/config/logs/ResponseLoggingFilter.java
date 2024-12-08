package com.codestorykh.config.logs;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Component
public class ResponseLoggingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Log response status and headers
            logger.info("Response Status Code: {}", response.getStatusCode());
            logger.info("Response Headers: {}", response.getHeaders());
            
            // Optionally, you can also log the response body, but that can be more complex (streaming response)
        }));
    }
}
