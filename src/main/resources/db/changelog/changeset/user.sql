-- Liquibase formatted SQL
-- changeset ivar:create-user-table
CREATE TABLE users
(
    id     SERIAL PRIMARY KEY,
    sub    VARCHAR(255)
);