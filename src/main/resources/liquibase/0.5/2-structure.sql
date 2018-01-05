--liquibase formatted sql

--changeset shrralis:1514300724438-1
ALTER TABLE map_markers ALTER COLUMN lat TYPE FLOAT8 USING (lat::FLOAT8);

--changeset shrralis:1514300724438-2
ALTER TABLE map_markers ALTER COLUMN lng TYPE FLOAT8 USING (lng::FLOAT8);

