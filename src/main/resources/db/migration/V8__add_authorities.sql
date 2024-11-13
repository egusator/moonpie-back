CREATE TABLE authority
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK ( name <> '' ),
    description VARCHAR(255) NOT NULL CHECK ( name <> '' )
);

CREATE TABLE client_authority
(
    client_id BIGINT,
    authority_id BIGINT,
    PRIMARY KEY (client_id, authority_id),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (authority_id) REFERENCES authority(id)
);

INSERT INTO authority(name, description) VALUES
('USER', 'Пользователь без привелегий'),
('EMPLOYEE', 'Сотрудник'),
('ADMIN', 'Пользователь с привелегиями');