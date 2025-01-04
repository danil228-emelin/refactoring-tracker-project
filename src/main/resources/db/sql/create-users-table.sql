CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    role_oid INTEGER            NOT NULL,
    password TEXT               NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_oid) REFERENCES roles (id),
    CHECK (username <> '')
);