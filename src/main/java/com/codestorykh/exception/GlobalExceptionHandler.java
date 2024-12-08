package com.codestorykh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PathNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ApiResponse<Object>> handlePathNotFound(PathNotFoundException ex) {
        return Mono.just(ApiResponse.error("404", ex.getMessage()));
    }

    @ExceptionHandler(UriNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ApiResponse<Object>> handleUriNotFound(UriNotFoundException ex) {
        return Mono.just(ApiResponse.error("404", ex.getMessage()));
    }

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ApiResponse<Object>> handleRouteNotFoundException(RouteNotFoundException ex) {
        return Mono.just(ApiResponse.error("404", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
        return Mono.just(ApiResponse.error("500", "Unexpected error occurred: " + ex.getMessage()));
    }
}
