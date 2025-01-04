CREATE TABLE IF NOT EXISTS cargoes
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR(32) NOT NULL,
    destination_center_oid INTEGER     NOT NULL,
    reception_center_oid   INTEGER     NOT NULL,
    cargo_type             VARCHAR(32) NOT NULL,
    registration_date      TIMESTAMP   NOT NULL DEFAULT now(),
    cargo_status_oid       INTEGER     NOT NULL,
    weight                 SMALLINT    NOT NULL,
    label_oid              INTEGER     NOT NULL,
    order_oid              INTEGER     NOT NULL,
    CONSTRAINT fk_destination_location FOREIGN KEY (destination_center_oid) REFERENCES locations (id),
    CONSTRAINT fk_source_location FOREIGN KEY (reception_center_oid) REFERENCES locations (id),
    CONSTRAINT fk_cargo_status FOREIGN KEY (cargo_status_oid) REFERENCES cargo_status (id),
    CONSTRAINT fk_label FOREIGN KEY (label_oid) REFERENCES labels (id),
    CONSTRAINT fk_order FOREIGN KEY (order_oid) REFERENCES orders (id),
    CONSTRAINT unique_cargo_status UNIQUE (cargo_status_oid),
    CONSTRAINT unique_label UNIQUE (label_oid)
);