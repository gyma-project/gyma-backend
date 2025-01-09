# Gyma Back-end

O GYMA (Gestão e Monitoramento de Academia) é uma solução tecnológica para otimizar a administração de academias. Ele oferece ferramentas integradas para gerenciar cadastros, matrículas, agendamentos, frequência, pagamentos e planos de treino personalizados. Com relatórios analíticos e recursos para monitoramento de treinos, o GYMA promove eficiência na gestão, engajamento dos clientes e suporte à tomada de decisão estratégica, modernizando a rotina das academias e melhorando a experiência de usuários e gestores.

## Como executar

O aplicativo spring roda na porta `8081` e o keycloak `8080`.

Para executar o Keycloak, use o comando abaixo:

`
docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.0.7 start-dev
`
