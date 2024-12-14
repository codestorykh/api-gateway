package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RouteApiRequest(Long id,
                              String uri,
                              String path,
                              String method,
                              String description,
                              @JsonProperty("group_code") String groupCode,
                              @JsonProperty("rate_limit") Integer rateLimit,
                              @JsonProperty("rate_limit_duration") Integer rateLimitDuration,
                              String status) {
}
