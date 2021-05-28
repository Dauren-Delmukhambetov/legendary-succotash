INSERT INTO users(email, first_name, last_name, password, created_at, deleted_at)
VALUES ('adam.smith@gmail.com', 'Adam', 'Smith', 'pa$$w0rd', current_timestamp, null),
       ('john.locke@gmail.com', 'John', 'Locke', 'pa$$w0rd', current_timestamp, null),
       ('david.ricardo@gmail.com', 'David', 'Ricardo', 'pa$$w0rd', current_timestamp, null),
       ('jean-baptiste.say@gmail.com', 'Jean-Baptiste', 'Say', 'pa$$w0rd', current_timestamp, current_timestamp),
       ('jeremy.bentham@gmail.com', 'Jeremy', 'Bentham', 'pa$$w0rd', current_timestamp, null),
       ('john.keynes@gmail.com', 'John Maynard', 'Keynes', 'pa$$w0rd', current_timestamp, null),
       ('milton.friedman@gmail.com', 'Milton', 'Friedman', 'pa$$w0rd', current_timestamp, null),
       ('robert.lucas.jr@gmail.com', 'Robert', 'Lucas Jr.', 'pa$$w0rd', current_timestamp, null),
       ('elinor.ostrom@gmail.com', 'Elinor', 'Ostrom', 'pa$$w0rd', current_timestamp, null),
       ('eugen.vbb@gmail.com', 'Eugen', 'von Böhm-Bawerk', 'pa$$w0rd', current_timestamp, null),
       ('alfred.marshall@gmail.com', 'Alfred', 'Marshall', 'pa$$w0rd', current_timestamp, null);

INSERT INTO user_roles(role, user_id)
SELECT 'USER', id FROM users WHERE email like '%gmail.com';

