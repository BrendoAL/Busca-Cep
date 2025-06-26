# 📫API Busca-Cep

Este é um projeto de API RESTful para consulta de CEPs brasileiros através de uma base de dados armazenada localmente em MySQL.

📐## Arquitetura utilizada

Separação em camadas MVC (Controller, Service e Repository, Model e Exceptions)  
Injeção de dependências com Spring

📘## Padrões de projetos utilizados

Repository: utilizado para abstrair a camada de acesso ao banco de dados.
Service layer: centraliza as regras de negócio e as chamadas ao repositório, separando a lógica de controle da lógica de domínio.

## ✔ Tecnologias e bibliotecas usadas

- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- MySQL  
- H2 Database  
- Lombok  
- JUnit 5  
- Mockito
- Postman
- Docker

## 🔎Endpoints

`GET /zipcode/{cep}`: endpoint para buscar um endereço pelo cep.

## 🛠 Execução do projeto

Para executar o projeto, é necessário ter o Java 21,Docker e Docker Compose instalados.

## 🚀 Como usar

📁Clone o repositório:  

git clone https://github.com/BrendoAL/Busca-Cep

  
Entre na pasta do projeto:  

cd Busca-Cep

  
Execute o comando abaixo para compilar e empacotar o projeto:  

mvn clean package

🐳Execute o docker-compose:

docker-compose up --build

Após ela ativar o status READY, a API estará disponível em `http://localhost:8080`.  
Um exemplo de busca de cep:
`http://localhost:8080/zipcode/69010001`.

## 🧪Testes unitários

Para rodar os testes unitários, execute o comando abaixo: 

mvn test

