


# Instruções para execução

## Levantar container com banco de dados:

```
docker-compose up
```

## Configurar IP do banco de dados no projeto:
```
src\main\resources\application.properties
```

Editar para que seja o mesmo IP do container:
```
spring.datasource.url=jdbc:postgresql://${DATABASE_HOST:192.168.99.100}:${DATABASE_PORT:5432}/votos
```


## Build Teste e execução

mvn clean package

java -jar votos-back-0.0.1-SNAPSHOT.jar

# Métodos

Métods da API:

## Cadastrar uma nova pauta

Obrigatório: titulo e número assembleia
POST localhost:8080/novapauta
```
{
    "titulo" : "titulo pauta",
    "descricao" :  "descricao pauta",
    "assembleia" : 3
}
```

Retorna os dados cadastrados com o ID gerado:
```
{
    "id": 50,
    "titulo": "titulo pauta",
    "descricao": "descricao pauta",
    "assembleia": 3,
    "inicioVotacao": null,
    "fimVotacao": null
}
```


## Abrir sessão de votação


POST localhost:8080/abrevotacao
```
{
    "pauta" : 3,
    "tempo" :  1
}
```

Retorna os dados cadastrados.


## Enviar voto

opcao = SIM ou NAO

POST localhost:8080/voto
```
{
    "pauta" : 3,
    "associado" : 8,
    "opcao" : "NAO"
}
```

Retorna os dados cadastrados.


## Contabilizar votos


GET localhost:8080/resulltado/3

Retorno:
```
[
    {
        "pauta": 50,
        "opcao": "NAO",
        "quantidade": 1
    },
    {
        "pauta": 50,
        "opcao": "SIM",
        "quantidade": 1
    }
]
```