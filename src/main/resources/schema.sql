CREATE SEQUENCE IF NOT EXISTS api_route_id_seq start with 1;

CREATE TABLE IF NOT EXISTS api_route (
    id BIGINT PRIMARY KEY DEFAULT nextval('api_route_id_seq'),
    uri VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    group_code VARCHAR(255) NOT NULL,
    rate_limit BIGINT NOT NULL,
    rate_limit_duration BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);