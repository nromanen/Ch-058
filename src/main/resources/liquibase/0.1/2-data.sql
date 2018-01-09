--liquibase formatted sql

--changeset shrralis:1513597385307-16
INSERT INTO users (login, email, password)
VALUES ('shrralis', 'shrralis@gmail.com', 'f2e81c4317845b0668f77e9790eda74f');
