DELETE FROM posts;
DELETE FROM users;

INSERT INTO users (id, username, display_name, email, password, role) VALUES
(1, 'lina', 'Lina Karlsson', 'lina@example.se', '$2b$10$Hpq3LrIwzZVu.VoqhxgJEeI2E1RJYHrcicORGMi6QFE1LGQXm63kK', 'USER'),
(2, 'amir', 'Amir Haddad', 'amir@example.se', '$2b$10$G0oJvCr1LlmhyeCd0Hv1ouM5puoz4yIgg/wm22PZ9UEIpveXD5/jy', 'USER');

INSERT INTO users (username, display_name, email, password, role) VALUES
('admin', 'Admin User', 'admin@example.se', '$2b$10$FNDfiysdgc9UKJ9Uyl849e2v3Kw.AstqhgNuDb0acEV0ErjVoLnne', 'ADMIN');

INSERT INTO posts (id, title, content, user_id) VALUES
(1, 'Välkommen till GrannSnack!', 'Det här är den första posten i systemet.', 1),
(2, 'Hej alla!', 'Kul att träffas här i vårt grannskap!', 2);
