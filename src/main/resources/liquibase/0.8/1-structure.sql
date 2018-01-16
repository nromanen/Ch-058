--liquibase formatted sql

--changeset fatiz:1515495601701-1
CREATE TABLE message (id SERIAL NOT NULL, text VARCHAR, userid INT NOT NULL, issueid INT NOT NULL, date TIMESTAMP WITHOUT TIME ZONE, authorid INT NOT NULL, CONSTRAINT message_pkey PRIMARY KEY (id));

--changeset fatiz:1515495601701-2
CREATE TABLE notification (login VARCHAR NOT NULL, userid INT NOT NULL, issueid INT NOT NULL, text VARCHAR, id SERIAL NOT NULL, waiting BOOLEAN, CONSTRAINT notification_id_pk PRIMARY KEY (id));

