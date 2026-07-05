# RestauranteTech — Sistema de Gestión de Pedidos (Microservicios)

Sistema de gestión de menú y pedidos para una cadena de restaurantes, construido con
Spring Boot, PostgreSQL, Docker Compose y Nginx como API Gateway.

## Arquitectura

| Componente     | Tecnología             | Puerto (host) |
|----------------|-------------------------|---------------|
| API Gateway    | Nginx                   | 8080          |
| svc-menu       | Spring Boot + PostgreSQL| 8081 (interno)|
| svc-orders     | Spring Boot + PostgreSQL| 8082 (interno)|
| svc-inventory  | Spring Boot + PostgreSQL| 8083 (interno)|
| db-menu        | PostgreSQL              | 5433          |
| db-orders      | PostgreSQL              | 5434          |
| db-inventory   | PostgreSQL              | 5435          |

Todo el tráfico externo entra únicamente por el API Gateway (puerto 8080). Los
microservicios no se exponen directamente al host, solo se comunican entre sí
dentro de la red Docker `restaurantetech-network`.

## Cómo ejecutar

```bash
docker-compose up --build
```

Esto levanta las 3 bases de datos PostgreSQL, los 3 microservicios y el API Gateway.

Para detener:

```bash
docker-compose down
```

Para eliminar también los volúmenes de datos:

```bash
docker-compose down -v
```

## Endpoints (a través del Gateway — http://localhost:8080)

### svc-menu

| Método | Ruta                        | Body                                  |
|--------|------------------------------|----------------------------------------|
| POST   | /api/menu/dishes             | `{ "name", "description", "category", "price", "available" }` |
| GET    | /api/menu/dishes             | —                                      |
| GET    | /api/menu/dishes/{id}        | —                                      |
| PUT    | /api/menu/dishes/{id}        | Dish actualizado                       |
| DELETE | /api/menu/dishes/{id}        | —                                      |

Ejemplo de creación de plato:

```json
POST http://localhost:8080/api/menu/dishes
{
  "name": "Ceviche de Camarón",
  "description": "Ceviche fresco con camarón y limón",
  "category": "ENTRADA",
  "price": 8.5,
  "available": true
}
```

### svc-orders

| Método | Ruta                     | Body                                            |
|--------|--------------------------|--------------------------------------------------|
| POST   | /api/orders               | `{ "customerName", "dishId", "quantity" }`      |
| GET    | /api/orders                | —                                                |
| GET    | /api/orders/{id}           | —                                                |
| PUT    | /api/orders/{id}/confirm   | (punto extra) valida y descuenta inventario      |

Ejemplo de pedido válido (plato existente y disponible):

```json
POST http://localhost:8080/api/orders
{
  "customerName": "Mesa 5",
  "dishId": 1,
  "quantity": 2
}
```

Ejemplo de pedido inválido (plato inexistente → 404):

```json
POST http://localhost:8080/api/orders
{
  "customerName": "Mesa 5",
  "dishId": 999,
  "quantity": 1
}
```

Respuesta esperada:

```json
{
  "timestamp": "...",
  "status": 404,
  "message": "El plato con id 999 no existe en el menú."
}
```

Si el plato existe pero `available = false`, se retorna 400 con:
`"El plato '{name}' no está disponible actualmente."`

### svc-inventory (punto extra)

| Método | Ruta                             | Body / Params            |
|--------|-----------------------------------|---------------------------|
| POST   | /api/inventory                     | `{ "dishId", "stockQuantity" }` |
| GET    | /api/inventory                     | —                          |
| GET    | /api/inventory/{dishId}            | —                          |
| PUT    | /api/inventory/{dishId}/decrement  | `?quantity=n`              |

`svc-orders` consulta a `svc-inventory` al confirmar un pedido
(`PUT /api/orders/{id}/confirm`), verifica stock suficiente y lo decrementa.

## Flujo de comunicación entre microservicios

1. Cliente → Nginx (8080) → `svc-orders` (8082): `POST /api/orders`.
2. `svc-orders` → `svc-menu` (8081): `GET /api/menu/dishes/{dishId}` vía `WebClient`.
3. Si el plato no existe → 404 descriptivo. Si no está disponible → 400 descriptivo.
4. Si es válido, `svc-orders` calcula `total = price * quantity` y persiste el pedido.
5. (Extra) Al confirmar el pedido, `svc-orders` consulta y actualiza `svc-inventory`.

## Estructura del repositorio

```
restaurantetech/
├── docker-compose.yml
├── nginx.conf
├── svc-menu/
├── svc-orders/
└── svc-inventory/   (punto extra)
```

## Integrantes

- Nombre 1
- Nombre 2
