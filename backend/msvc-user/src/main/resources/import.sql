INSERT INTO RY_USER (NAME, LASTNAME, AGE, EMAIL, PASSWORD) VALUES ('Jose', 'Limachi', 23, 'jose@mail', '$2a$10$5AtRM6jkKLhhpful.tuonO1xiw8LLaUIT4WFK6Z3tDaH7.Z.seoR.');

INSERT INTO RY_ROLE (name, description) VALUES ('ROLE_ADMIN', 'System Administrator');
INSERT INTO RY_ROLE (name, description) VALUES ('ROLE_USER', 'Default role for new users');

INSERT INTO RY_USER_ROLE (user_id, role_id) VALUES (1, 1);
