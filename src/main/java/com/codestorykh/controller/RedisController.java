package com.codestorykh.controller;

import com.codestorykh.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/{key}")
    public Mono<String> getValue(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @PostMapping("/{key}")
    public Mono<Boolean> setValue(@PathVariable String key, @RequestBody String value) {
        return redisService.setValue(key, value);
    }

    @DeleteMapping("/{key}")
    public Mono<Boolean> deleteKey(@PathVariable String key) {
        return redisService.deleteKey(key);
    }
}
