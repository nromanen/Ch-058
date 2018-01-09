--liquibase formatted sql

--changeset shrralis:1513597385307-1
CREATE TYPE IMAGE_TYPE AS ENUM ('USER', 'ISSUE');

--changeset shrralis:1513597385307-2
CREATE TYPE USER_TYPE AS ENUM ('BANNED', 'USER', 'ADMIN', 'MASTER');

--changeset shrralis:1513597385307-3
CREATE TABLE images (
  id   SERIAL       NOT NULL,
  src  VARCHAR(128) NOT NULL,
  type IMAGE_TYPE   NOT NULL,
  hash VARCHAR(32)  NOT NULL,
  CONSTRAINT images_pkey PRIMARY KEY (id)
);

--changeset shrralis:1513597385307-4
CREATE TABLE users (
  id       SERIAL                   NOT NULL,
  login    VARCHAR(16)              NOT NULL,
  type     USER_TYPE DEFAULT 'USER' NOT NULL,
  email    VARCHAR(256)             NOT NULL,
  password VARCHAR(32)              NOT NULL,
  image_id INT,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

--changeset shrralis:1513597385307-5
ALTER TABLE images
  ADD CONSTRAINT images_hash_key UNIQUE (hash);

--changeset shrralis:1513597385307-6
ALTER TABLE images
  ADD CONSTRAINT images_src_key UNIQUE (src);

--changeset shrralis:1513597385307-7
ALTER TABLE images
  ADD CONSTRAINT unique_image_hash UNIQUE (hash);

--changeset shrralis:1513597385307-8
ALTER TABLE images
  ADD CONSTRAINT unique_image_id UNIQUE (id);

--changeset shrralis:1513597385307-9
ALTER TABLE images
  ADD CONSTRAINT unique_image_src UNIQUE (src);

--changeset shrralis:1513597385307-10
ALTER TABLE users
  ADD CONSTRAINT unique_user_email UNIQUE (email);

--changeset shrralis:1513597385307-11
ALTER TABLE users
  ADD CONSTRAINT unique_user_id UNIQUE (id);

--changeset shrralis:1513597385307-12
ALTER TABLE users
  ADD CONSTRAINT unique_user_login UNIQUE (login);

--changeset shrralis:1513597385307-13
ALTER TABLE users
  ADD CONSTRAINT users_email_key UNIQUE (email);

--changeset shrralis:1513597385307-14
ALTER TABLE users
  ADD CONSTRAINT users_login_key UNIQUE (login);

--changeset shrralis:1513597385307-15
ALTER TABLE users
  ADD CONSTRAINT users_image_id_fkey FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE CASCADE ON DELETE CASCADE;