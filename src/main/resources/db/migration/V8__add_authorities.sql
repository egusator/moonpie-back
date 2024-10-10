CREATE TABLE authority
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK ( name <> '' ),
    description VARCHAR(255) NOT NULL CHECK ( name <> '' )
);

CREATE TABLE client_authority
(
    client_id BIGINT REFERENCES client,
    authority_id BIGINT REFERENCES authority,
    PRIMARY KEY (client_id, authority_id)
);

INSERT INTO roles(name, description) VALUES
('USER', 'Пользователь без привелегий'),
('EMPLOYEE', 'Сотрудник'),
('ADMIN', 'Пользователь с привелегиями');