package com.codestorykh.repository;

import com.codestorykh.model.ApiRoute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ApiRouteRepository extends R2dbcRepository<ApiRoute, Long> {

    Mono<ApiRoute> findFirstById(Long id);

    Mono<Void> deleteAllById(Long id);

    @Query("""
    UPDATE api_route SET uri = :uri, path = :path, method = :method, description = :description, 
    group_code =: groupCode, status = :status, updated_by = :updatedBy, updated_at = NOW() 
    WHERE id = :id RETURNING *
    """)
    Mono<ApiRoute> updateRoute(Long id, String uri, String path, String method, String description,
                              String groupCode, String status, String updatedBy);
}
