INSERT INTO RY_USER (NAME, LASTNAME, AGE, EMAIL, PASSWORD) VALUES ('Jose', 'Limachi', 23, 'jo.limachi@email.com', '$2a$10$qAiH1Td5qASTXi5Eh66CS.Fr2x0./AnvjKfeP7X.gzc0b3fuGM3A.');

INSERT INTO RY_ROLE (name, description) VALUES ('ROLE_ADMIN', 'System Administrator');
INSERT INTO RY_ROLE (name, description) VALUES ('ROLE_USER', 'Default role for new users');

INSERT INTO RY_USER_ROLE (user_id, role_id) VALUES (1, 1);
