Desafio Técnico – Backend (Spring Boot + Kafka + PostgreSQL)

Objetivo:
Criar um microserviço em Spring Boot (Java) que consuma mensagens de um tópico Kafka, processe os dados e armazene-os em um PostgreSQL, expondo uma API REST para consulta.

Requisitos mínimos:
1.	Consumir dados simulados (ex.: temperatura, umidade) de um tópico Kafka.
2.	Processar/validar as mensagens recebidas.
3.	Persistir os dados em um banco PostgreSQL.
4.	Expor endpoints REST para consultar os dados armazenados.

Entregáveis:
•	Código hospedado em repositório Git com instruções claras no README.md para subir e testar o projeto (incluindo Kafka e PostgreSQL via Docker Compose).
•	Estrutura de banco criada via migrations (Flyway ou Liquibase).
•	Pelo menos alguns testes automatizados.

---

