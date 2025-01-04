CREATE TABLE IF NOT EXISTS incidents
(
    id              SERIAL PRIMARY KEY,
    cargo_oid       INTEGER     NOT NULL,
    type            VARCHAR(32) NOT NULL,
    description     TEXT        NOT NULL,
    occurrence_date TIMESTAMP   NOT NULL DEFAULT now(),
    CONSTRAINT fk_cargo_incident FOREIGN KEY (cargo_oid) REFERENCES cargoes (id)
);