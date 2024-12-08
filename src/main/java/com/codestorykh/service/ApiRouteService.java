package com.codestorykh.service;

import com.codestorykh.dto.RouteApiRequest;
import com.codestorykh.dto.RouteApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {

    Mono<RouteApiResponse> create(RouteApiRequest routeApiRequest);

    Mono<RouteApiResponse> update(Long id, RouteApiRequest routeApiRequest);

    Flux<RouteApiResponse> findAll();

    Mono<RouteApiResponse> findById(Long id);

    Mono<Void> delete(Long id);

    Mono<Void> deleteAll();
}
