CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(20) UNIQUE,
    apelido VARCHAR(50),
    senha TEXT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);
