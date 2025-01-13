# CDI Java

## Creación del contenedor en Docker para MongoDB

`docker run --name mongoprueba -p 27017:27017 -d mongo:latest`

`docker exec -it mongoprueba bash`

`mongosh`

`test> show dbs`

`test> use productos`

Copiar el texto del JSON: transferencias_db2.json

`productos> db.productos.insertMany(transferencias_db2.json)`

```javascript
productos> db.productos.insertOne({
    "id": 4,
    "nombre": "producto4",
    "descripcion": "Producto de prueba 4",
    "precio": 120.0
})
```
