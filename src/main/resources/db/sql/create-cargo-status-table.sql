CREATE TABLE IF NOT EXISTS cargo_status
(
    id                    SERIAL PRIMARY KEY,
    location_oid          INTEGER     NOT NULL,
    update_time           TIMESTAMP   NOT NULL,
    cargo_status VARCHAR(32) NOT NULL,
    CONSTRAINT fk_location FOREIGN KEY (location_oid) REFERENCES locations (id)
);