-- Creating first user
INSERT INTO users (login, email, password)
VALUES ('shrralis', 'shrralis@gmail.com', 'f2e81c4317845b0668f77e9790eda74f');
-- Adding unavailable image
-- TODO: change hash
INSERT INTO images (id, src, type, hash) VALUES (0, 'default.png', 'ISSUE', 'f2e81c4317845b0668f77e9790eda74f');
-- Creating user for testing
INSERT INTO users (login, email, password, image_id)
VALUES ('test', 'test@test.test', '098f6bcd4621d373cade4e832627b4f6', 0);