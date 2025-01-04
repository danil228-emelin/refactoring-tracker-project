CREATE TABLE IF NOT EXISTS notifications
(
    id          SERIAL PRIMARY KEY,
    description TEXT    NOT NULL,
    is_positive BOOLEAN NOT NULL,
    owner_oid   INTEGER NOT NULL,
    CONSTRAINT fk_destination FOREIGN KEY (owner_oid) REFERENCES users (id)
);