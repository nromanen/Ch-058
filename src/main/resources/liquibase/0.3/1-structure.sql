--liquibase formatted sql

--changeset skindrtc:1513870893333-1
CREATE TABLE user_provider (id SERIAL NOT NULL, user_id INT NOT NULL, provider VARCHAR NOT NULL, provider_uid VARCHAR NOT NULL, CONSTRAINT user_provider_pkey PRIMARY KEY (id));

--changeset skindrtc:1513870893333-2
ALTER TABLE user_provider ADD CONSTRAINT user_provider_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

