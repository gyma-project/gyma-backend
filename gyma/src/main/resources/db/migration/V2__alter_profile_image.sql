-- 1. Remover a chave estrangeira da tabela profiles
ALTER TABLE public.profiles DROP CONSTRAINT profiles_image_id_fkey;

-- 2. Remover a coluna image_id da tabela profiles
ALTER TABLE public.profiles DROP COLUMN image_id;

-- 3. Adicionar a nova coluna image_url
ALTER TABLE public.profiles ADD COLUMN image_url VARCHAR(255);

-- 4. Remover a tabela images (se não for mais utilizada)
DROP TABLE IF EXISTS public.images;
