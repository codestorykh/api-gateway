package com.codestorykh.service.impl;

import com.codestorykh.model.ApiRoute;
import com.codestorykh.repository.ApiRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class RateLimiterService {

    private final StringRedisTemplate stringRedisTemplate;

    private final ApiRouteRepository apiRouteRepository;


    private static final String LUA_SCRIPT =
            "local key = KEYS[1] " +
                    "local limit = tonumber(ARGV[1]) " +
                    "local window = tonumber(ARGV[2]) " +
                    "local current = redis.call('INCR', key) " +
                    "if current == 1 then " +
                    "    redis.call('EXPIRE', key, window) " +
                    "end " +
                    "if current > limit then " +
                    "    return 0 " +
                    "else " +
                    "    return 1 " +
                    "end";

    public RateLimiterService(StringRedisTemplate stringRedisTemplate,
                              ApiRouteRepository apiRouteRepository) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.apiRouteRepository = apiRouteRepository;
    }


    public Mono<Boolean> isAllowed(String path, String method, String identifier) {
        return apiRouteRepository.findFirstByPathAndMethod(path, method)
                .flatMap(routeConfig -> {
                    if (routeConfig.getRateLimit() == null) {
                        return Mono.just(true); // No rate limit for this route
                    }

                    // Construct Redis key (identifier:path:method)
                    String redisKey = String.format("%s:%s:%s", identifier, path, method);

                    // Execute Lua script to enforce rate limit
                    return Mono.fromCallable(() -> stringRedisTemplate.execute(
                            RedisScript.of(LUA_SCRIPT, Long.class),
                            Collections.singletonList(redisKey),
                            routeConfig.getRateLimit().toString(),
                            routeConfig.getRateLimitDuration().toString()
                    )).map(result -> result != null && result == 1);
                })
                .defaultIfEmpty(true); // Default to true if no route configuration is found
    }

}
