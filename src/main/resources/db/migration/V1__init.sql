CREATE TABLE users(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name varchar(50) UNIQUE NOT NULL,
    last_name varchar (50),
    email varchar (255) UNIQUE NOT NULL,
    password varchar (50) UNIQUE NOT NULL,
    phone varchar (50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE user_roles(
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role varchar (50) UNIQUE NOT NULL,
    user_id int,
    FOREIGN KEY(user_id) REFERENCES users(id)
)