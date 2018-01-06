--liquibase formatted sql

--changeset shrralis:1515072393075-1
ALTER TABLE users
  ADD failed_auth_count INT DEFAULT 0 NOT NULL;
