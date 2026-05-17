# Perfumería - Tomás González

Sistema de gestión para una perfumería, desarrollado con arquitectura de microservicios en Spring Boot. Permite administrar clientes, perfumes, inventario, proveedores y ventas de forma centralizada a través de un API Gateway.

---

## Integrantes

- Tomás Benjamín González Adriazola

---

## Aplicaciones requeridas

- Java JDK 21
- Maven
- Spring Boot
- MySQL (XAMPP)
- VS Code
- Postman

---

## Microservicios

| Microservicio | Puerto | Base de datos |
|---|---|---|
| clientesservice | 8081 | basecliente |
| inventarioservice | 8082 | db_inventario |
| perfumesservice | 8083 | db_perfumes |
| proveedoresservice | 8084 | db_proveedores |
| ventasservice | 8085 | db_ventas |
| apigateway | 9090 | - |

---

## Cómo ejecutar el proyecto

### 1. Requisitos previos
- Tener XAMPP corriendo con MySQL activo
- Tener Java 21 instalado

### 2. Clonar el repositorio
```bash
git clone <https://github.com/dankcris/Perfumeria-TomasGonzalez-005D.git>
```

### 3. Ejecutar cada microservicio

Abrir cada proyecto en VS Code y presionar el botón **Run** (▶️) en el archivo `Application.java` de cada microservicio, o bien usar la terminal:

```bash
cd Perfumeria/clientesservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/inventarioservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/perfumesservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/proveedoresservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/ventasservice
./mvnw spring-boot:run
```
```bash
cd Perfumeria/apigateway
./mvnw spring-boot:run
```

Las bases de datos se crean automáticamente al iniciar cada microservicio.

---

## Endpoints disponibles

Todos los endpoints pueden consumirse directamente por su puerto o a través del Gateway en `http://localhost:9090`.

### Clientes `http://localhost:8081/api/clientes`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/clientes | Lista todos los clientes |
| GET | /api/clientes/{id} | Busca cliente por ID |
| POST | /api/clientes | Crea un cliente |
| DELETE | /api/clientes/{id} | Elimina un cliente |

### Inventario `http://localhost:8082/api/inventario`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/inventario | Lista todo el inventario |
| POST | /api/inventario | Agrega un registro de inventario |

### Perfumes `http://localhost:8083/api/perfumes`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/perfumes | Lista todos los perfumes |
| GET | /api/perfumes/{id} | Busca perfume por ID |
| POST | /api/perfumes | Crea un perfume |

### Proveedores `http://localhost:8084/api/proveedores`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/proveedores | Lista todos los proveedores |
| POST | /api/proveedores | Crea un proveedor |

### Ventas `http://localhost:8085/api/ventas`

| Método | Endpoint | Descripción |
|---|---|---|
| POST | /api/ventas | Crea una venta |

---

## Datos de prueba

### Crear un cliente
```json
POST http://localhost:9090/api/clientes
{
  "nombre": "Tomás González",
  "correo": "tomas@gmail.com",
  "telefono": "912345678"
}
```

### Crear un perfume
```json
POST http://localhost:9090/api/perfumes
{
  "nombre": "Sauvage",
  "marca": "Dior",
  "precio": 89990,
  "stock": 50
}
```

### Crear un registro de inventario
```json
POST http://localhost:9090/api/inventario
{
  "nombrePerfume": "Sauvage",
  "stock": 50
}
```

### Crear un proveedor
```json
POST http://localhost:9090/api/proveedores
{
  "nombre": "Juan Pérez",
  "empresa": "Distribuidora Fragance",
  "telefono": "987654321"
}
```

### Crear una venta
```json
POST http://localhost:9090/api/ventas
{
  "clienteId": 1,
  "perfumeId": 1,
  "cantidad": 1
}
```

El total de la venta se calcula automáticamente multiplicando el precio del perfume por la cantidad.

---

## Comunicación entre microservicios

- **Ventas** consulta a **Clientes** (puerto 8081) para verificar que el cliente exista.
- **Ventas** consulta a **Perfumes** (puerto 8083) para obtener el precio y calcular el total automáticamente.
- El **Gateway** (puerto 9090) centraliza el acceso a todos los microservicios.

---

## Reglas de negocio

- El total de una venta se calcula automáticamente: `precio del perfume × cantidad`.
- No se puede registrar una venta si el cliente o el perfume no existen.