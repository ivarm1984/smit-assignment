-- Liquibase formatted SQL
-- changeset ivar:create-book-table
CREATE TABLE book
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);