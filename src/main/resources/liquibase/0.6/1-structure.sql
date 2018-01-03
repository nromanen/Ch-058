--liquibase formatted sql

--changeset shrralis:1514896494730-1
CREATE TABLE recovery_tokens (id SERIAL NOT NULL, token VARCHAR(32) NOT NULL, user_id INT NOT NULL, expires_at TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT recovery_tokens_pkey PRIMARY KEY (id));

--changeset shrralis:1514896494730-2
ALTER TABLE recovery_tokens ADD CONSTRAINT recovery_tokens_token_key UNIQUE (token);

--changeset shrralis:1514896494730-3
ALTER TABLE recovery_tokens ADD CONSTRAINT recovery_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;
