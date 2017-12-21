--liquibase formatted sql

--changeset shrralis:1513873275236-1
ALTER TABLE issues
  ADD created_at TIMESTAMP(6) WITHOUT TIME ZONE DEFAULT NOW() NOT NULL;

--changeset shrralis:1513873275236-2
ALTER TABLE issues
  ADD updated_at TIMESTAMP(6) WITHOUT TIME ZONE;

