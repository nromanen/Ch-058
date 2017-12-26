--liquibase formatted sql

--changeset shrralis:1514034820344-1
ALTER TABLE users ALTER COLUMN password TYPE VARCHAR(64) USING (password::VARCHAR(64));

