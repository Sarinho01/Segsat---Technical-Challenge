## Desafio Técnico – Backend (Spring Boot + Kafka + PostgreSQL)

## Objetivo:
Criar um microserviço em Spring Boot (Java) que consuma mensagens de um tópico Kafka, processe os dados e armazene-os em um PostgreSQL, expondo uma API REST para consulta.

## Requisitos mínimos:
1.	Consumir dados simulados (ex.: temperatura, umidade) de um tópico Kafka.
2.	Processar/validar as mensagens recebidas.
3.	Persistir os dados em um banco PostgreSQL.
4.	Expor endpoints REST para consultar os dados armazenados.

## Entregáveis:
•	Código hospedado em repositório Git com instruções claras no README.md para subir e testar o projeto (incluindo Kafka e PostgreSQL via Docker Compose).
•	Estrutura de banco criada via migrations (Flyway ou Liquibase).
•	Pelo menos alguns testes automatizados.

---

# Como inicializar o projeto

## 1. Clonar o repositório
```bash

git clone <url-do-repositorio>
cd <nome-do-projeto>
```

## 2. Criar o arquivo de ambiente
Copie o arquivo `.env.example` e renomeie para `.env`:
```bash

cp .env.example .env
```
Nenhuma alteração é necessária para rodar o projeto, nem é recomendado que se altere algo.

## 3. Subir o projeto
```bash

docker compose up --build
```
Certifique-se, para evitar conflitos, de que não há containers rodando ao mesmo tempo

---

# Fluxo da aplicação

O microserviço consome métricas enviadas ao tópico Kafka `device-measurement-topic`.  
Cada mensagem é validada e salva no banco de dados.

A API permite:
- Buscar uma medição por **ID**.
- Pesquisar medições de forma paginada (10 itens por página), podendo filtrar por `deviceId`.

---

# Enviando uma mensagem ao tópico do Kafka

A solução inclui o **Kafka UI**, acessível em:

**http://localhost:8085**

## Como produzir mensagens:
1. Abra o menu **Topics** localizado no canto esquerdo superior.
2. Selecione o tópico **device-measurement-topic**.
3. Clique em **Produce Message**.
4. Envie o JSON no campo *value* com a estrutura:

```json
{
  "deviceId": 1,
  "temperature": 0.1,
  "humidity": 0.1,
  "pressure": 0.1
}
```

O campo **key** pode ter qualquer valor.  
A API consumirá automaticamente a mensagem.

---

# API – Endpoints REST

Documentação Swagger:  
**http://localhost:8000/swagger-ui/index.html**

## Buscar medição por ID
**MÉTODO HTTP GET**
```
/api/v1/device-measurement/{id}
```
Retorna a medição correspondente ao ID informado.

**OBS**: {ID} é uma path variable

## Buscar medições paginadas
**MÉTODO HTTP GET**
```
/api/v1/device-measurement/search?page=&deviceId=
```
- `page` → página (começa em 0)
- `deviceId` → opcional; filtra por dispositivo

Retorna até 10 medições por página.

**OBS**: Após o ? é inserido query parameters.

---

# Testes automatizados
Os testes estão no diretório:
```
src/test/java
```