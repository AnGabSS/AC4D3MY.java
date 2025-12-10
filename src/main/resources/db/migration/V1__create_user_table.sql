-- ==== Tabelas de Usuários e Roles ====
CREATE SEQUENCE IF NOT EXISTS roles_seq START 1 INCREMENT 50;
CREATE TABLE IF NOT EXISTS roles (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('roles_seq'),
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT roles_name_check CHECK (name IN ('ADMIN', 'USER'))
);

CREATE SEQUENCE IF NOT EXISTS users_seq START 1 INCREMENT 50;
CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('users_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    birthdate DATE,
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT LOCALTIMESTAMP,
    updated_at TIMESTAMP DEFAULT LOCALTIMESTAMP
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Insere as roles básicas se não existirem
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER') ON CONFLICT (name) DO NOTHING;

