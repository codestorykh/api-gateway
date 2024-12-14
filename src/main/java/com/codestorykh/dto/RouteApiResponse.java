package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RouteApiResponse(Long id,
                               String uri,
                               String path,
                               String method,
                               String description,
                               @JsonProperty("group_code") String groupCode,
                               @JsonProperty("rate_limit") Integer rateLimit,
                               @JsonProperty("rate_limit_duration") Integer rateLimitDuration,
                               String status,
                               @JsonProperty("created_by") String createdBy,
                               @JsonProperty("created_at") String createdAt,
                               @JsonProperty("updated_by") String updatedBy,
                               @JsonProperty("updated_at") String updatedAt)
{
}
