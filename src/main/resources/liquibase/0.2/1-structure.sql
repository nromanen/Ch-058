--liquibase formatted sql

--changeset shrralis:1513600434496-1
CREATE TABLE issues (
  id            SERIAL        NOT NULL,
  map_marker_id INT           NOT NULL,
  author_id     INT           NOT NULL,
  title         VARCHAR(32)   NOT NULL,
  text          VARCHAR(2048) NOT NULL,
  CONSTRAINT issues_pkey PRIMARY KEY (id)
);

--changeset shrralis:1513600434496-2
CREATE TABLE issues_votes (
  issue_id INT                  NOT NULL,
  voter_id INT                  NOT NULL,
  vote     BOOLEAN DEFAULT TRUE NOT NULL
);

--changeset shrralis:1513600434496-3
CREATE TABLE map_markers (
  id  SERIAL NOT NULL,
  lat FLOAT4 NOT NULL,
  lng FLOAT4 NOT NULL,
  CONSTRAINT map_markers_pkey PRIMARY KEY (id)
);

--changeset shrralis:1513600434496-4
ALTER TABLE users
  ADD name VARCHAR(16) DEFAULT ' ' NOT NULL;

--changeset shrralis:1513600434496-5
ALTER TABLE users
  ADD surname VARCHAR(32) DEFAULT ' ' NOT NULL;

--changeset shrralis:1513600434496-6
ALTER TABLE issues_votes
  ADD CONSTRAINT issues_votes_pkey PRIMARY KEY (issue_id, voter_i d);

--changeset shrralis:1513600434496-7
ALTER TABLE issues
  ADD CONSTRAINT unique_issue_id UNIQUE (id);

--changeset shrralis:1513600434496-8
ALTER TABLE issues_votes
  ADD CONSTRAINT unique_issue_vote_id UNIQUE (issue_id, voter_id);

--changeset shrralis:1513600434496-9
ALTER TABLE map_markers
  ADD CONSTRAINT unique_map_marker_id UNIQUE (id);

--changeset shrralis:1513600434496-10
ALTER TABLE map_markers
  ADD CONSTRAINT unique_map_marker_lat_lng UNIQUE (lat, lng);

--changeset shrralis:1513600434496-11
ALTER TABLE issues
  ADD CONSTRAINT issues_author_id_fkey FOREIGN KEY (author_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset shrralis:1513600434496-12
ALTER TABLE issues
  ADD CONSTRAINT issues_map_marker_id_fkey FOREIGN KEY (map_marker_id) REFERENCES map_markers (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset shrralis:1513600434496-13
ALTER TABLE issues_votes
  ADD CONSTRAINT issues_votes_issue_id_fkey FOREIGN KEY (issue_id) REFERENCES issues (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset shrralis:1513600434496-14
ALTER TABLE issues_votes
  ADD CONSTRAINT issues_votes_voter_id_fkey FOREIGN KEY (voter_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

