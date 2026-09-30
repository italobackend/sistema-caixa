CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(256) NOT NULL,
    username   VARCHAR(50)  NOT NULL,
    password   VARCHAR(256) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    created_in TIMESTAMP    NOT NULL
);