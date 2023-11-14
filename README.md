<h1>Escopo, pré-requisitos e objetivo</h1>

<h3><p>Projeto que serve de referência para o desenvolvimento de software em Java.</p></h3>

É um projeto proposto para ser clonado e reutilizado, para ilustrar o projeto inclui:<br>
(a) Transação de cartão formada por orientação objeto com uma entidade principal e duas dependentes;<br>
(b) REST API para realizar pagamentos mediantes a cartões sendo avista ou parcelado.<br>
(c) modelo de post e response:

Post:
~~~
{
  "transacaoModel": {
    "cartao": 1234567890.00,
    "descricaoModel":{
        "valor": 99.00,
        "dataHora": "2021-01-01T18:30:00",
        "estabelecimento": "PetShop Mundo Cão"
    },
    "formaPagamentoModel":{
        "tipo": "PARCELADO_EMISSOR",
        "parcelas": 2
    }
  }
}
~~~ 
Response:
~~~
{
    "id": "4551d3cc-7344-411d-ab0b-219b73d4f8bc",
    "cartao": 1234567890,
    "descricaoModel": {
        "valor": 99.0,
        "dataHora": "2021-01-01T18:30:00",
        "estabelecimento": "PetShop Mundo Cão",
        "nsu": 0.025241642690565502,
        "codigoAutorizacao": 0.9847618033971305,
        "status": "AUTORIZADO"
    },
    "formaPagamentoModel": {
        "tipo": "PARCELADO_EMISSOR",
        "parcelas": 2
    }
}
~~~
Estorno:
~~~
{
    "id": "c90f552d-b7ec-4eaf-89f0-6bb1c81e9f61",
    "cartao": 1234567890,
    "descricaoModel": {
        "valor": 99.0,
        "dataHora": "2021-01-01T18:30:00",
        "estabelecimento": "PetShop Mundo Cão",
        "nsu": null,
        "codigoAutorizacao": null,
        "status": "CANCELADO"
    },
    "formaPagamentoModel": {
        "tipo": "PARCELADO_EMISSOR",
        "parcelas": 2
    }
}
~~~

<h3>Iniciando...</h3>
- jdk18
- https://github.com/luizfellipes/ToolsChallange.git
- baixar todas as dependencias solicitada no pom.xml
- Banco de Dados Relacional em memoria H2
- Swagger http://localhost:8080/swagger-ui/index.html#/

<h3>Executando a aplicação</h3>

Como se trata de uma aplicação teste rode ela pela classe SpringApplication.run, compile ou execute o Dockerfile.

docker build -t toolschallange .<br>
docker run -p 8080:8080 toolschallange

<h3>Objetivo</h3>
Criar uma API Rest para estudos baseado em java com framework SpringBoot

