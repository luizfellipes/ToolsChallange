<h1>Escopo, pré-requisitos e objetivo.</h1>

<h3><p>Projeto que serve de referência para o desenvolvimento de software em Java.</p></h3>

É um projeto proposto para ser clonado e reutilizado, para ilustrar o projeto inclui:<br>
(a) Transação de cartão formada por orientação objeto com uma entidade principal e duas dependentes;<br>
(b) REST API para realizar pagamentos mediantes a cartões sendo avista ou parcelado.<br>
(c) modelo de post e response:

Post:
~~~
{
    "cartao": 1424214,
    "descricaoDePagamento":{
        "valor": 100,
        "dataHora": "2021-01-25T18:30:00",
        "estabelecimento": "PetShop Mundo Cão"
    },
    "formaDePagamento":{
        "tipo": "AVISTA",
        "parcelas": 1
    }
}
~~~ 
Response:
~~~
{
	"id": "7d968765-8d6d-4740-9912-2ee08304954c",
	"cartao": 1424214,
	"descricaoModel": {
		"valor": 100.0,
		"dataHora": "2021-01-25T18:30:00",
		"estabelecimento": "PetShop Mundo Cão",
		"nsu": 0.3862791307688558,
		"codigoAutorizacao": 0.8936644582119375,
		"status": "AUTORIZADO"
	},
	"formaPagamentoModel": {
		"tipo": "AVISTA",
		"parcelas": 1
	}
}
~~~
Estorno:
~~~
{
	"id": "0aeb7540-274e-4c9c-8f13-aa62643646a6",
	"cartao": 1424214,
	"descricaoModel": {
		"valor": 100.0,
		"dataHora": "2021-01-25T18:30:00",
		"estabelecimento": "PetShop Mundo Cão",
		"nsu": null,
		"codigoAutorizacao": null,
		"status": "CANCELADO"
	},
	"formaPagamentoModel": {
		"tipo": "AVISTA",
		"parcelas": 1
	}
}
~~~

<h3>Iniciando...</h3>
- jdk17
- https://github.com/luizfellipes/ToolsChallange.git
- baixar todas as dependencias solicitada no pom.xml
- Banco de Dados Relacional em memoria H2
- Swagger http://localhost:8080/swagger-ui/index.html#/

<h3>Executando a aplicação</h3>

Como se trata de uma aplicação teste rode ela pela classe SpringApplication.run, compile ou execute o Dockerfile.

docker build -t toolschallange .<br>
docker run -p 8080:8080 toolschallange

<h3>Objetivo</h3>
Criar uma API Rest para estudo baseado em java com framework SpringBoot

