-- Ativa a extensão pgcrypto para gerar UUID (se ainda não estiver ativa)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Adiciona a coluna external_id do tipo UUID, com valor padrão gerado automaticamente
ALTER TABLE usuarios
ADD COLUMN external_id UUID NOT NULL DEFAULT gen_random_uuid();

-- Adiciona colunas para controle de timestamps
ALTER TABLE usuarios
ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW();

ALTER TABLE usuarios
ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE usuarios
ADD COLUMN deleted_at TIMESTAMP WITHOUT TIME ZONE;

-- Garante que external_id seja único
CREATE UNIQUE INDEX ux_usuarios_external_id ON usuarios(external_id);


-- se nao der boa deve rodar no banco antes de executar " CREATE EXTENSION IF NOT EXISTS "pgcrypto"; "
