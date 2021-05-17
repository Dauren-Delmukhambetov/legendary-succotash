DELETE
FROM user_roles;
DELETE
FROM users;

INSERT INTO users(email, password, created_at)
VALUES ('admin@admin.com', '$2a$12$mbrKKshlIeJQNszD4KJSjeXCAZGbIMrcDG8qkiPkwTXy2G4dNXzrW', current_timestamp),
       ('user@user.com', '$2a$12$mbrKKshlIeJQNszD4KJSjeXCAZGbIMrcDG8qkiPkwTXy2G4dNXzrW', current_timestamp);
INSERT INTO user_roles(role, user_id)
VALUES ('ADMIN', (SELECT id FROM users WHERE email = 'admin@admin.com'));
INSERT INTO user_roles(role, user_id)
VALUES ('ADMIN', (SELECT id FROM users WHERE email = 'user@user.com'));

