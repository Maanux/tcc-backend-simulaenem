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

CREATE TABLE provas_questoes (
  id SERIAL PRIMARY KEY,
  prova_id INTEGER REFERENCES provas(id) ON DELETE CASCADE,
  questao_id INTEGER REFERENCES questoes(id),
  ordem INTEGER,
  tempo_gasto INTERVAL DEFAULT INTERVAL '0',
  alternativa_respondida CHAR(1),
  correta BOOLEAN
);
