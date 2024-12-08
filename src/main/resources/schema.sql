CREATE TABLE IF NOT EXISTS api_route (
    id SERIAL PRIMARY KEY,
    uri VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    group_code VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL
);