# 🍦 Icecream API Manager - Gestão de Sorveterias

Uma API completa para gerenciamento de sorveterias, com autenticação JWT, controle de usuários e CRUD de produtos. Desenvolvida em **Spring Boot** com boas práticas de segurança e documentação Swagger.

---

## 📥 Como Executar o Projeto

Para executar o projeto no terminal:
```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes:
```shell script
mvn clean test
```

Acessar o endereço e visualizar a execução do projeto:
```
http://localhost:8080/api/v1/icecream
```

### Pré-requisitos
- Java 8+
- Maven 3.8+
- PostgreSQL ou H2 (configurável)

## 🚀 Funcionalidades Principais

| Método       | Endpoint               | Descrição                          | Acesso       |
|--------------|------------------------|------------------------------------|-------------|
| `POST` 🔑     | `/auth/login`          | Autenticação com JWT               | Público      |
| `POST` 📝     | `/auth/register`       | Registro de novos usuários         | ADMIN        |
| `GET` 🗂️      | `/product`             | Listar todos os produtos           | USER/ADMIN   |
| `POST` 🆕     | `/product`             | Criar novo produto                 | ADMIN        |
| `PUT` ✏️      | `/product/{id}`        | Atualizar produto                  | ADMIN        |
| `DELETE` 🗑️  | `/product/{id}`        | Excluir produto                    | ADMIN        |




