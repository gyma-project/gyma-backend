# GYMA - Gestão e Monitoramento de Academia (Back-end) 🏋️‍♂️
O GYMA é um sistema para otimizar a administração de academias, oferecendo ferramentas para gerenciamento de cadastros, matrículas, agendamentos, frequência, pagamentos e planos de treino. O sistema também disponibiliza relatórios analíticos e suporte à tomada de decisão estratégica, proporcionando uma experiência moderna e eficiente para gestores e alunos.

## 🚀 Tecnologias Utilizadas
- Java 21 + Spring Boot
- PostgreSQL (Banco de dados)
- Keycloak (Gerenciamento de autenticação e autorização)
- MinIO (Armazenamento de imagens)
- Docker (Containerização)
- Swagger (Documentação da API)


## 🔧 Configuração e Execução

1. Clonar o repositório `https://github.com/gyma-project/gyma-backend.git`

2. Instale o Keycloak: `docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.0.7 start-dev`

    1. Acesse o keycloak em: [http://localhost:8080](http://localhost:8080/)

3. Swagger

    1. Acesse o swagger em: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)


4. Para instalar o MinIO, use o comando:
`
mkdir -p ~/minio/data
docker run \
   -p 9000:9000 \
   -p 9001:9001 \
   --name minio \
   -v ~/minio/data:/data \
   -e "MINIO_ROOT_USER=ROOT" \
   -e "MINIO_ROOT_PASSWORD=ROOTROOT" \
   quay.io/minio/minio server /data --console-address ":9001"
`

    1. Acesse o MinIO em: [http://localhost:9000](http://localhost:9001/)
