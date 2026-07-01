#  Perfumería - Tomás González

Sistema de gestión para una perfumería desarrollado con **arquitectura de microservicios en Spring Boot**. Permite administrar clientes, perfumes, inventario, proveedores, ventas, pagos, envíos, categorías y promociones de forma centralizada a través de un API Gateway con autenticación JWT.

---

##  Integrantes

- Tomás Benjamín González Adriazola

---

##  Herramientas y tecnologías

| Herramienta | Versión | Uso |
|---|---|---|
| Java JDK | 21 | Lenguaje principal |
| Spring Boot | 3.x | Framework backend |
| Spring Security | 6.x | Autenticación y seguridad |
| Spring Cloud Gateway | 4.x | API Gateway |
| OpenFeign | 4.x | Comunicación entre microservicios |
| JWT (jjwt) | 0.11.5 | Tokens de autenticación |
| JUnit 5 | 5.x | Testing unitario |
| Mockito | 5.x | Mocks para testing |
| Springdoc OpenAPI | 2.2.0 | Documentación Swagger |
| MySQL | 10.4 (MariaDB) | Base de datos |
| XAMPP | - | Servidor local MySQL |
| Maven | 3.x | Gestor de dependencias |
| VS Code | - | IDE |
| Postman | - | Pruebas de API |

---

##  Microservicios

| Microservicio | Puerto | Base de datos | Descripción |
|---|---|---|---|
| `autenticacionservice` | 8086 | `db_autenticacion` | Autenticación JWT y gestión de usuarios |
| `clientesservice` | 8081 | `basecliente` | Gestión de clientes |
| `inventarioservice` | 8082 | `db_inventario` | Control de inventario |
| `perfumesservice` | 8083 | `db_perfumes` | Catálogo de perfumes |
| `proveedoresservice` | 8084 | `db_proveedores` | Gestión de proveedores |
| `ventasservice` | 8085 | `db_ventas` | Registro de ventas |
| `categoriasservice` | 8087 | `db_categorias` | Categorías de productos |
| `promocionesservice` | 8088 | `db_promociones` | Promociones y descuentos |
| `pagosservice` | 8089 | `db_pagos` | Gestión de pagos |
| `enviosservice` | 8090 | `db_envios` | Gestión de envíos |
| `apigateway` | 9090 | - | Puerta de entrada centralizada |

---

##  Cómo ejecutar el proyecto

### 1. Requisitos previos
- Java JDK 21 instalado
- XAMPP corriendo con MySQL activo
- Maven instalado (o usar el `mvnw` incluido)

### 2. Clonar el repositorio
```bash
git clone https://github.com/dankcris/Perfumeria-TomasGonzalez-005D.git
cd Perfumeria-TomasGonzalez-005D
```

### 3. Importar la base de datos
En phpMyAdmin, importar el archivo:
Perfumeria/database/perfumeria_database.sql

### 4. Ejecutar los microservicios

Ejecutar en este orden (el autenticacionservice y apigateway primero):

```bash
cd Perfumeria/autenticacionservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/apigateway
./mvnw spring-boot:run
```
```bash
cd Perfumeria/clientesservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/perfumesservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/ventasservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/inventarioservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/proveedoresservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/pagosservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/enviosservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/categoriasservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/promocionesservice
./mvnw spring-boot:run
```

---

##  Autenticación

Todos los endpoints (excepto login y register) requieren un token JWT en el header:
Authorization: Bearer <token>

### Obtener token
POST http://localhost:9090/api/auth/login
Content-Type: application/json
{
"username": "admin",
"password": "1234"
}

---

##  Endpoints disponibles

Todos los endpoints son accesibles a través del Gateway en `http://localhost:9090`

###  Autenticación
| Método | Endpoint | Descripción |
|---|---|---|
| POST | /api/auth/login | Iniciar sesión y obtener token JWT |
| POST | /api/auth/register | Registrar nuevo usuario |

###  Clientes `http://localhost:9090/api/clientes`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/clientes | Lista todos los clientes |
| GET | /api/clientes/{id} | Busca cliente por ID |
| POST | /api/clientes | Crea un cliente |
| PUT | /api/clientes/{id} | Actualiza un cliente |
| DELETE | /api/clientes/{id} | Elimina un cliente |

###  Perfumes `http://localhost:9090/api/perfumes`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/perfumes | Lista todos los perfumes |
| GET | /api/perfumes/{id} | Busca perfume por ID |
| POST | /api/perfumes | Crea un perfume |
| PUT | /api/perfumes/{id} | Actualiza un perfume |
| DELETE | /api/perfumes/{id} | Elimina un perfume |

###  Ventas `http://localhost:9090/api/ventas`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/ventas | Lista todas las ventas |
| GET | /api/ventas/{id} | Busca venta por ID |
| POST | /api/ventas | Crea una venta |
| PUT | /api/ventas/{id} | Actualiza una venta |
| DELETE | /api/ventas/{id} | Elimina una venta |

###  Inventario `http://localhost:9090/api/inventario`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/inventario | Lista el inventario |
| POST | /api/inventario | Agrega registro de inventario |

###  Proveedores `http://localhost:9090/api/proveedores`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/proveedores | Lista todos los proveedores |
| GET | /api/proveedores/{id} | Busca proveedor por ID |
| POST | /api/proveedores | Crea un proveedor |
| PUT | /api/proveedores/{id} | Actualiza un proveedor |
| DELETE | /api/proveedores/{id} | Elimina un proveedor |

###  Pagos `http://localhost:9090/api/pagos`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/pagos | Lista todos los pagos |
| GET | /api/pagos/{id} | Busca pago por ID |
| POST | /api/pagos | Registra un pago |
| PUT | /api/pagos/{id} | Actualiza un pago |
| DELETE | /api/pagos/{id} | Elimina un pago |

###  Envíos `http://localhost:9090/api/envios`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/envios | Lista todos los envíos |
| GET | /api/envios/{id} | Busca envío por ID |
| POST | /api/envios | Crea un envío |
| PUT | /api/envios/{id} | Actualiza un envío |
| DELETE | /api/envios/{id} | Elimina un envío |

###  Categorías `http://localhost:9090/api/categorias`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/categorias | Lista todas las categorías |
| GET | /api/categorias/{id} | Busca categoría por ID |
| POST | /api/categorias | Crea una categoría |
| PUT | /api/categorias/{id} | Actualiza una categoría |
| DELETE | /api/categorias/{id} | Elimina una categoría |

###  Promociones `http://localhost:9090/api/promociones`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/promociones | Lista todas las promociones |
| GET | /api/promociones/{id} | Busca promoción por ID |
| POST | /api/promociones | Crea una promoción |
| PUT | /api/promociones/{id} | Actualiza una promoción |
| DELETE | /api/promociones/{id} | Elimina una promoción |

---

##  Swagger UI

Documentación centralizada de todos los microservicios a través del API Gateway:

**http://localhost:9090/webjars/swagger-ui/index.html**

Desde ahí puedes seleccionar cualquier microservicio en el dropdown superior.

También accesible individualmente por cada microservicio:

| Microservicio | URL Swagger |
|---|---|
| autenticacionservice | http://localhost:8086/swagger-ui.html |
| clientesservice | http://localhost:8081/swagger-ui.html |
| inventarioservice | http://localhost:8082/swagger-ui.html |
| perfumesservice | http://localhost:8083/swagger-ui.html |
| proveedoresservice | http://localhost:8084/swagger-ui.html |
| ventasservice | http://localhost:8085/swagger-ui.html |
| categoriasservice | http://localhost:8087/swagger-ui.html |
| promocionesservice | http://localhost:8088/swagger-ui.html |
| pagosservice | http://localhost:8089/swagger-ui.html |
| enviosservice | http://localhost:8090/swagger-ui.html |

---

##  Testing

Se testearon 8 de los 10 microservicios con JUnit 5 y Mockito (80% de cobertura):

| Microservicio | Tests |
|---|---|
| autenticacionservice | ✅ 4 tests |
| categoriasservice | ✅ 5 tests |
| promocionesservice | ✅ 5 tests |
| pagosservice | ✅ 5 tests |
| enviosservice | ✅ 5 tests |
| clientesservice | ✅ 6 tests |
| perfumesservice | ✅ 6 tests |
| ventasservice | ✅ 6 tests |

Para ejecutar los tests de un microservicio:
```bash
cd Perfumeria/clientesservice
./mvnw test
```

---

##  Ejemplos de datos de prueba

### Crear un cliente
```json
POST http://localhost:9090/api/clientes
Authorization: Bearer <token>
{
  "nombre": "Tomás González",
  "correo": "tomas@gmail.com",
  "telefono": "912345678"
}
```

### Crear un perfume
```json
POST http://localhost:9090/api/perfumes
Authorization: Bearer <token>
{
  "nombre": "Sauvage",
  "marca": "Dior",
  "precio": 89990,
  "stock": 50
}
```

### Crear una venta
```json
POST http://localhost:9090/api/ventas
Authorization: Bearer <token>
{
  "clienteId": 1,
  "perfumeId": 1,
  "cantidad": 2
}
```

El total se calcula automáticamente: `precio × cantidad`

---

##  Comunicación entre microservicios

- **ventasservice** consulta a **clientesservice** (puerto 8081) para verificar que el cliente exista
- **ventasservice** consulta a **perfumesservice** (puerto 8083) para obtener el precio y calcular el total
- **pagosservice** consulta a **ventasservice** (puerto 8085) para verificar la venta
- **enviosservice** consulta a **ventasservice** (puerto 8085) para verificar la venta
- El **apigateway** (puerto 9090) centraliza el acceso y valida el token JWT en cada petición

##  Comunicación entre microservicios

- **ventasservice** consulta a **clientesservice** (puerto 8081) para verificar que el cliente exista
- **ventasservice** consulta a **perfumesservice** (puerto 8083) para obtener el precio y calcular el total
- **pagosservice** consulta a **ventasservice** (puerto 8085) para verificar la venta
- **enviosservice** consulta a **ventasservice** (puerto 8085) para verificar la venta
- El **apigateway** (puerto 9090) centraliza el acceso y valida el token JWT en cada petición
