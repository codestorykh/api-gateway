package com.codestorykh.controller;

import com.codestorykh.dto.RouteApiRequest;
import com.codestorykh.dto.RouteApiResponse;
import com.codestorykh.exception.ApiResponse;
import com.codestorykh.exception.RouteNotFoundException;
import com.codestorykh.service.ApiRouteService;
import com.codestorykh.service.impl.GatewayRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/routers",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class ApiRouterRestController {

    private final ApiRouteService apiRouteService;
    private final GatewayRouteService gatewayRouteService;

    @PostMapping("/refresh-routes")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> refreshRoutes() {
        log.info("Refreshing routes");
        gatewayRouteService.refreshRoutes();
        return Mono.empty();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> create(@RequestBody RouteApiRequest routeApiRequest) {
        log.info("Creating route: {}", routeApiRequest);
        return apiRouteService.create(routeApiRequest)
                .map(ApiResponse::success)
                .onErrorResume(e -> {
                    log.error("Error occurred during route creation: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to create route: " + e.getMessage()));
                });
    }


    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> update(@PathVariable Long id,
                                                      @RequestBody RouteApiRequest routeApiRequest) {
        log.info("Updating route with id: {} with request: {}", id, routeApiRequest);

        return apiRouteService.update(id, routeApiRequest)
                .map(ApiResponse::success)
                .onErrorResume(RouteNotFoundException.class, e -> {
                    log.error("Route not found: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("404", e.getMessage()));
                })
                .onErrorResume(e -> {
                    log.error("Error occurred during route update: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to update route: " + e.getMessage()));
                });
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<List<RouteApiResponse>>> findAll() {
        log.info("Fetching all routes");
        return apiRouteService.findAll()
                .collectList()
                .map(ApiResponse::success)
                .onErrorResume(e -> {
                    log.error("Error occurred during route get all: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to get routes: " + e.getMessage()));
                });
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<RouteApiResponse>> findById(@PathVariable Long id) {
        log.info("Fetching route by id: {}", id);
        return apiRouteService.findById(id)
                .map(ApiResponse::success)
                .onErrorResume(e -> {
                    log.error("Error occurred during route get by id: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to get route: " + e.getMessage()));
                });
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Deleting route by id: {}", id);
        return apiRouteService.delete(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAll() {
        log.info("Deleting all routes");
        return apiRouteService.deleteAll();
    }
}
