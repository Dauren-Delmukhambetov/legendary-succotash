INSERT INTO users(id, email, password, created_at) OVERRIDING SYSTEM VALUE
VALUES (1, 'admin@admin.com', '$2a$12$mbrKKshlIeJQNszD4KJSjeXCAZGbIMrcDG8qkiPkwTXy2G4dNXzrW', current_timestamp),
       (2, 'user@user.com', '$2a$12$mbrKKshlIeJQNszD4KJSjeXCAZGbIMrcDG8qkiPkwTXy2G4dNXzrW', current_timestamp);
INSERT INTO user_roles(role, user_id)
VALUES ('ADMIN', (SELECT id FROM users WHERE email = 'admin@admin.com'));
INSERT INTO user_roles(role, user_id)
VALUES ('USER', (SELECT id FROM users WHERE email = 'user@user.com'));

