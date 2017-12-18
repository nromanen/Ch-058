--liquibase formatted sql

--changeset shrralis:1513609008270-1
CREATE TABLE issue_types (
  id   SERIAL      NOT NULL,
  name VARCHAR(16) NOT NULL,
  CONSTRAINT issue_types_pkey PRIMARY KEY (id)
);

--changeset shrralis:1513609008270-2
ALTER TABLE issues
  ADD image_id INT;

--changeset shrralis:1513609008270-3
ALTER TABLE issues
  ADD type_id INT NOT NULL;

--changeset shrralis:1513609008270-4
ALTER TABLE issues
  ADD closed BOOLEAN DEFAULT FALSE NOT NULL;

--changeset shrralis:1513609008270-5
ALTER TABLE issue_types
  ADD CONSTRAINT issue_types_name_key UNIQUE (name);

--changeset shrralis:1513609008270-6
ALTER TABLE issues
  ADD CONSTRAINT issues_image_id_fkey FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset shrralis:1513609008270-7
ALTER TABLE issues
  ADD CONSTRAINT issues_type_id_fkey FOREIGN KEY (type_id) REFERENCES issue_types (id) ON UPDATE CASCADE ON DELETE CASCADE;

