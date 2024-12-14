package com.codestorykh.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_route")
public class ApiRoute implements Serializable {

    @Id
    private Long id;

    private String uri;
    private String path;
    private String method;
    private String description;
    @Column(name = "group_code")
    private String groupCode;
    @Column(name = "rate_limit")
    private Integer rateLimit;
    @Column(name = "rate_limit_duration")
    private Integer rateLimitDuration;
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
