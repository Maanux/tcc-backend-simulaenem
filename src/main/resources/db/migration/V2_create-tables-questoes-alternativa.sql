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
    alternatives_introduction TEXT
);

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
