# Spring RabbitMQ Microservices

Este repositório contém microserviços integrados com RabbitMQ, Eureka e Keycloak para autenticação. Siga as instruções abaixo para configurar e executar os serviços.

## Requisitos

- **Docker**: Para rodar Keycloak e RabbitMQ.
- **Java 17+**: Para rodar os microserviços.
- **Maven**: Para construir os serviços.

## Configuração

### 1. Subir o Keycloak
Na pasta do Keycloak, já existe um **realm** configurado. Para iniciar o Keycloak e importar o realm:

```bash
docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin --network network-credito quay.io/keycloak/keycloak:26.0.2 start-dev
```

Após iniciar o container, acesse o Keycloak em [localhost:8081](http://localhost:8081) e importe o realm localizado na pasta do projeto.

### 2. Subir o RabbitMQ

Execute o RabbitMQ em Docker e crie a fila `emissao-cartoes`:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

Acesse [localhost:15672](http://localhost:15672), faça login (usuário: guest, senha: guest) e crie a fila `emissao-cartoes`.

## Ordem de Inicialização dos Microserviços

1. **Eureka**: Inicie primeiro para registro dos serviços.
2. **Gateway**: Para roteamento de requisições.
3. **Outros Serviços**: Ordem não importa após Eureka e Gateway.

## Endpoints Disponíveis

### Serviço de Clientes (`localhost:8080/clientes`)

- **GET /clientes**: Lista todos os clientes.
- **GET /clientes?cpf=12345678900**: Consulta cliente pelo CPF.
- **POST /clientes**: Adiciona um cliente.
  - Body:
    ```json
    {
      "nome": "Fulano",
      "cpf": 12345678900,
      "idade": 22
    }
    ```

### Serviço de Cartões (`localhost:8080/cartoes`)

- **POST /cartoes**: Adiciona um cartão.
  - Body:
    ```json
    {
      "nome": "Bradesco Maste",
      "bandeira": "MASTERCARD",
      "renda": 6000,
      "limite": 8000
    }
    ```
- **GET /cartoes?renda=10000**: Lista cartões para renda até o valor especificado.
- **GET /cartoes?cpf=12345678900**: Consulta cartões pelo CPF do cliente.

### Serviço de Avaliação de Crédito (`localhost:8080/avaliacoes-credito`)

- **GET /avaliacoes-credito/situacao-cliente?cpf=12345678900**: Consulta a situação do cliente.
- **POST /avaliacoes-credito/solicitacoes-cartao**: Solicita emissão de cartão (requer autenticação via token Bearer).
  - Body:
    ```json
    {
      "cpf": "12345678900",
      "idCartao": Coloque o id que foi gerado na emissão do cartão,
      "endereco": "Rua x",
      "limiteLiberado": Veja o limite que foi liberado pelo endpoint avaliação cliente 
    }
    ```
- **POST /avaliacoes-credito**: Avalia cliente para crédito.
  - Body:
    ```json
    {
      "cpf": 12345678900,
      "renda": 5000
    }
    ```

## Autenticação

Para acessar o endpoint de emissão de cartão, obtenha o token JWT via Keycloak seguindo estas instruções:

1. **Grant type**: Client Credentials
2. **Access Token URL**: `http://localhost:8081/realms/mscourserealm/protocol/openid-connect/token`
3. **Client ID**: `msCredito`
4. **Client Secret**: Utilize a senha obtida no Keycloak para o client `msCredito` do realm `mscourserealm`.

---

