-- Ativa a extensão pgcrypto para uso de UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabela de usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(20) UNIQUE,
    apelido VARCHAR(50),
    senha TEXT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    external_id UUID NOT NULL DEFAULT gen_random_uuid(),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

-- Índice único para external_id dos usuários
CREATE UNIQUE INDEX ux_usuarios_external_id ON usuarios(external_id);

-- Tabela de questoes
CREATE TABLE questoes (
    id SERIAL PRIMARY KEY,
    title TEXT,
    index INTEGER,
    discipline TEXT NOT NULL CHECK (
        discipline IN ('ciencias-humanas', 'ciencias-natureza', 'linguagens', 'matematica')
    ),
    language TEXT,
    year INTEGER,
    context TEXT,
    files TEXT[],
    correct_alternative CHARACTER(1) CHECK (
        correct_alternative IN ('A', 'B', 'C', 'D', 'E')
    ),
    alternatives_introduction TEXT,
    external_id UUID NOT NULL DEFAULT gen_random_uuid(),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE
);

-- Tabela de alternativas
CREATE TABLE alternativas (
    id SERIAL PRIMARY KEY,
    questao_id INTEGER NOT NULL REFERENCES questoes(id) ON DELETE CASCADE,
    letter CHARACTER(1) NOT NULL CHECK (
        letter IN ('A', 'B', 'C', 'D', 'E')
    ),
    text TEXT,
    file TEXT,
    is_correct BOOLEAN
);

-- Tabela de provas
CREATE TABLE provas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    titulo VARCHAR(255),
    data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMP,
    tempo_total INTERVAL DEFAULT INTERVAL '0',
    status VARCHAR(20) DEFAULT 'em_andamento' CHECK (status IN ('em_andamento', 'pausada', 'finalizada')),
    ultima_atividade TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Relação entre provas e questoes
CREATE TABLE provas_questoes (
    id SERIAL PRIMARY KEY,
    prova_id INTEGER REFERENCES provas(id) ON DELETE CASCADE,
    questao_id INTEGER REFERENCES questoes(id),
    ordem INTEGER,
    tempo_gasto INTERVAL DEFAULT INTERVAL '0',
    alternativa_respondida CHAR(1),
    correta BOOLEAN
);
