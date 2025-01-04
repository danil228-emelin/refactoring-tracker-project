CREATE TABLE IF NOT EXISTS cargo_requests
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR(32),
    reception_center_oid   INTEGER     NOT NULL,
    destination_center_oid INTEGER     NOT NULL,
    creation_date          TIMESTAMP   DEFAULT now(),
    owner_oid              INTEGER     NOT NULL,
    cargo_type             VARCHAR(32) NOT NULL,
    CONSTRAINT fk_reception_center FOREIGN KEY (reception_center_oid) REFERENCES locations (id),
    CONSTRAINT fk_destination_center FOREIGN KEY (destination_center_oid) REFERENCES locations (id),
    CONSTRAINT fk_user FOREIGN KEY (owner_oid) REFERENCES users (id),
    CHECK (name <> '')
);
