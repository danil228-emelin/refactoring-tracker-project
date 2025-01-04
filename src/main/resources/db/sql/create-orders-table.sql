CREATE TABLE IF NOT EXISTS orders
(
    id            SERIAL PRIMARY KEY,
    client_oid    INTEGER   NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    delivery_date TIMESTAMP,
    CONSTRAINT fk_user_order FOREIGN KEY (client_oid) REFERENCES users (id)
);