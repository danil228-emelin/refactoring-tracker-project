CREATE TABLE IF NOT EXISTS locations
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(63) UNIQUE NOT NULL,
    address TEXT               NOT NULL,
    type    VARCHAR(32)        NOT NULL default 'STOCK',
    CHECK (name <> '')
);