CREATE TABLE products
(
    id            BIGSERIAL PRIMARY KEY,
    codigo        VARCHAR(100) NOT NULL UNIQUE,
    nome          VARCHAR(100) NOT NULL,
    quantidade    INT          NOT NULL CHECK (quantidade >= 0),
    preco         DECIMAL      NOT NULL CHECK (preco >= 0),
    marca         VARCHAR(100) NOT NULL,
    categoria     VARCHAR(100) NOT NULL,
    codigo_barras VARCHAR(14)  NOT NULL UNIQUE
)