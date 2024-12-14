package com.codestorykh.config.logs;

import com.codestorykh.service.impl.RateLimiterService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


@Component
public class RequestLoggingFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    private final RateLimiterService rateLimiterService;

    public RequestLoggingFilter(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String uri = request.getURI().toString();
        String path = request.getPath().toString();
        String method = request.getMethod().toString();
        // Check for X-Forwarded-For header
        /*
         If your application is behind a proxy or load balancer, the RemoteAddress might not return the actual client IP.
         In such cases, you should check the X-Forwarded-For or Forwarded headers.
        */
        String forwardedFor = request.getHeaders().getFirst("X-Forwarded-For");

        // If header exists, use it; otherwise, fall back to RemoteAddress
        String clientIp = forwardedFor != null
                ? forwardedFor.split(",")[0].trim() // Use the first IP in the list
                : request.getRemoteAddress() != null
                ? request.getRemoteAddress().getAddress().getHostAddress()
                : "Unknown";

        // Log incoming request details
        logger.info("Request URI: {}", uri);
        logger.info("Request Path: {}", path);
        logger.info("Request Method: {}", method);
        logger.info("Request Headers: {}", request.getHeaders());
        logger.info("Request Remote Address: {}", clientIp);

        return rateLimiterService.isAllowed(path, method, clientIp)  // Check if the request is allowed based on rate-limiting
                .flatMap(isAllowed -> {
                    if (!isAllowed) {
                        // If rate limit exceeded, return 429 status
                        logger.info("Rate limit exceeded. Returning 429 status...");

                        ServerHttpResponse response = exchange.getResponse();

                        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        String customResponse = "{" +
                                "\"success\": false," +
                                "\"errorCode\": \"429\"," +
                                "\"message\": \"Rate limit exceeded. Please try again later.\"," +
                                "\"data\": {}" +
                                "}";
                        byte[] bytes = customResponse.getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = response.bufferFactory().wrap(bytes);
                        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
                        return response.writeWith(Mono.just(buffer));
                    }
                    logger.info("Rate limit not exceeded. Forwarding request to the backend...");
                    return chain.filter(exchange);
                }).then();
    }
}
