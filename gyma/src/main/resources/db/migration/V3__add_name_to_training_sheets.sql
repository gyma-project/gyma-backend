-- Adiciona a coluna 'name' na tabela 'training_sheets'
ALTER TABLE public.training_sheets ADD COLUMN name VARCHAR(255) NOT NULL DEFAULT 'Treino Padrão';

-- Atualiza os valores antigos caso seja necessário
UPDATE public.training_sheets SET name = 'Treino Atualizado' WHERE name IS NULL;
