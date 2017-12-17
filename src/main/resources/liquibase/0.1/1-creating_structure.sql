--liquibase formatted sql
--changeset runInTransaction:false

-- Creating enum `IMAGE_TYPE`
CREATE TYPE IMAGE_TYPE AS ENUM ('USER', 'ISSUE');
-- Creating table `images`
CREATE TABLE images (
  id   SERIAL UNIQUE            NOT NULL PRIMARY KEY,
  src  VARCHAR(16) UNIQUE       NOT NULL,
  type IMAGE_TYPE               NOT NULL,
  hash VARCHAR(32) UNIQUE       NOT NULL
);
-- Creating indexes for table `image`
CREATE UNIQUE INDEX CONCURRENTLY images_id
  ON images (id);

CREATE UNIQUE INDEX CONCURRENTLY images_src
  ON images (src);

CREATE UNIQUE INDEX CONCURRENTLY images_hash
  ON images (hash);
-- Adding that indexes to the table
ALTER TABLE images
  ADD CONSTRAINT unique_image_id
  UNIQUE USING INDEX images_id;

ALTER TABLE images
  ADD CONSTRAINT unique_image_src
  UNIQUE USING INDEX images_src;

ALTER TABLE images
  ADD CONSTRAINT unique_image_hash
  UNIQUE USING INDEX images_hash;

-- Creating enum `USER_TYPE`
CREATE TYPE USER_TYPE AS ENUM ('BANNED', 'USER', 'ADMIN', 'MASTER');
-- Creating table `users`
CREATE TABLE users (
  id       SERIAL UNIQUE        NOT NULL PRIMARY KEY,
  login    VARCHAR(16) UNIQUE   NOT NULL,
  type     USER_TYPE            NOT NULL DEFAULT 'USER',
  email    VARCHAR(256) UNIQUE  NOT NULL,
  password VARCHAR(32)          NOT NULL,
  image_id INTEGER,
  FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- Creating unique indexes for table `user`
CREATE UNIQUE INDEX CONCURRENTLY users_id
  ON users (id);

CREATE UNIQUE INDEX CONCURRENTLY users_login
  ON users (login);

CREATE UNIQUE INDEX CONCURRENTLY users_email
  ON users (email);
-- Adding that indexes to the table
ALTER TABLE users
  ADD CONSTRAINT unique_user_id
  UNIQUE USING INDEX users_id;

ALTER TABLE users
  ADD CONSTRAINT unique_user_login
  UNIQUE USING INDEX users_login;

ALTER TABLE users
  ADD CONSTRAINT unique_user_email
  UNIQUE USING INDEX users_email;
