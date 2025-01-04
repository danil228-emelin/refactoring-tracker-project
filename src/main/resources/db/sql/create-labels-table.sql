CREATE TABLE IF NOT EXISTS labels
(
    id              SERIAL PRIMARY KEY,
    sscc_code       VARCHAR(18) UNIQUE NOT NULL,
    generation_date TIMESTAMP          NOT NULL DEFAULT now(),
    CHECK (sscc_code ~ '^\d{18}$')
);

CREATE INDEX idx_labels_sscc_code_hash ON labels USING hash (sscc_code);