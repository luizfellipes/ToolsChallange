<h1>Escopo, pré-requisitos e objetivo.</h1>

<h3><p>Projeto que serve de referência para o desenvolvimento de software em Java.</p></h3>

É um projeto proposto para ser clonado e reutilizado, para ilustrar o projeto inclui:<br>
(a) Transação de cartão formada por orientação objeto com uma entidade principal e duas dependentes, tratamento de erros  e teste unitarios;<br>
(b) REST API para realizar pagamentos mediantes a cartões sendo avista ou parcelado.<br>
(c) modelo de post e response:

Post:
~~~
{
    "cartao": 5165165156,
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
	"id": "56e90714-1d82-4bfa-9bdd-559c853a39d6",
	"cartao": 5165165156,
	"descricaoModel": {
		"valor": 100.0,
		"dataHora": "2021-01-25T18:30:00",
		"estabelecimento": "PetShop Mundo Cão",
		"nsu": 104.0,
		"codigoAutorizacao": 993.0,
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
	"id": "18f4fa82-6268-4d68-94d5-9de4220d36b8",
	"cartao": 5165165156,
	"descricaoModel": {
		"valor": 100.0,
		"dataHora": "2021-01-25T18:30:00",
		"estabelecimento": "PetShop Mundo Cão",
		"nsu": 149.0,
		"codigoAutorizacao": 320.0,
		"status": "CANCELADO"
	},
	"formaPagamentoModel": {
		"tipo": "AVISTA",
		"parcelas": 1
	},
	"_links": {
		"self": {
			"href": "http://localhost:8080/transacoes/18f4fa82-6268-4d68-94d5-9de4220d36b8"
		}
	}
}
~~~

GetAll Exemple
~~~

{
	"content": [
		{
			"id": "56e90714-1d82-4bfa-9bdd-559c853a39d6",
			"cartao": 5165165156,
			"descricaoModel": {
				"valor": 100.0,
				"dataHora": "2021-01-25T18:30:00",
				"estabelecimento": "PetShop Mundo Cão",
				"nsu": 104.0,
				"codigoAutorizacao": 993.0,
				"status": "AUTORIZADO"
			},
			"formaPagamentoModel": {
				"tipo": "AVISTA",
				"parcelas": 1
			},
			"links": [
				{
					"rel": "self",
					"href": "http://localhost:8080/transacoes/56e90714-1d82-4bfa-9bdd-559c853a39d6"
				}
			]
		}
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"empty": true,
			"unsorted": true
		},
		"pageNumber": 0,
		"pageSize": 10,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"first": true,
	"size": 10,
	"number": 0,
	"sort": {
		"sorted": false,
		"empty": true,
		"unsorted": true
	},
	"numberOfElements": 1,
	"empty": false
}

~~~

GetOne Exemple 
~~~
{
	"id": "56e90714-1d82-4bfa-9bdd-559c853a39d6",
	"cartao": 5165165156,
	"descricaoModel": {
		"valor": 100.0,
		"dataHora": "2021-01-25T18:30:00",
		"estabelecimento": "PetShop Mundo Cão",
		"nsu": 104.0,
		"codigoAutorizacao": 993.0,
		"status": "AUTORIZADO"
	},
	"formaPagamentoModel": {
		"tipo": "AVISTA",
		"parcelas": 1
	},
	"links": [
		{
			"rel": "self",
			"href": "http://localhost:8080/transacoes/56e90714-1d82-4bfa-9bdd-559c853a39d6"
		}
	]
}
~~~


<h3>Iniciando...</h3>

- jdk17
- clonar o projeto https://github.com/luizfellipes/ToolsChallange.git<br>
- baixar todas as dependencias solicitada no pom.xml<br>
- Banco de Dados Relacional em memoria H2<br>
- Swagger http://localhost:8080/swagger-ui/index.html#/
- SonarCube http://localhost:9000/sessions/new?return_to=%2F

<h3>Executando a aplicação</h3>

Como se trata de uma aplicação teste rode ela pela classe SpringApplication.run, compile ou execute o Dockerfile.<br>

docker build -t toolschallange .<br>
docker run -p 8080:8080 toolschallange

para o sonarCube
docker-compose up na pasta raiz do projeto

<h3>Objetivo</h3>
Criar uma API Rest para estudo baseado em java com framework SpringBoot, para estudo e conhecimento

