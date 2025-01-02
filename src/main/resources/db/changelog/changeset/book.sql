-- Liquibase formatted SQL
-- changeset ivar:create-book-table
CREATE TABLE book
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(255),
    author VARCHAR(255)
);