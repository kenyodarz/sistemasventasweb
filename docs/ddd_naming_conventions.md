# Naming Conventions en Java con DDD + Clean Architecture

## Objetivo
Definir convenciones claras para nombrar clases en un proyecto basado en:
- Domain-Driven Design (DDD)
- Clean Architecture
- Java

Este documento está optimizado para ser interpretado tanto por humanos como por agentes (IA).

---

# 1. Regla Principal

Los nombres de las clases dependen de la **capa arquitectónica**, no del tipo de dato.

---

# 2. Capas y Convenciones

## 2.1 API Layer (Entrada / Controllers)

### Propósito
Representar datos de entrada y salida en interfaces externas (HTTP, REST).

### Convenciones

- Entrada: `*Request`
- Salida: `*Response`

### Ejemplos

class CreateUserRequest:
    name: str
    email: str

class UserResponse:
    id: str
    name: str

### Reglas

- NO contienen lógica de negocio
- SOLO sirven para serialización/deserialización
- NO deben usarse fuera de la capa API

---

## 2.2 Application Layer (Casos de Uso)

### Propósito
Orquestar la lógica de negocio a través de casos de uso.

### Convenciones

- Entrada: `*Command`
- Salida: `*Result` (o `*Response` opcional)

### Command

class CreateUserCommand:
    name: str
    email: str

### Result

class CreateUserResult:
    userId: str

### Reglas

- Un `Command` representa una acción (verbo)
- No depende de HTTP ni frameworks
- Se usa en UseCases

---

## 2.3 Domain Layer (Modelo de Negocio)

### Propósito
Contener la lógica de negocio pura.

### Convenciones

- Entidades: sin sufijos
- Value Objects: sin sufijos
- Servicios de dominio: `*Service` (si aplica)

### Ejemplos

class User:
    id: str
    email: str

class Email:
    value: str

### Reglas

- NO usar:
  - `Request`
  - `Response`
  - `DTO`
  - `Data`
- NO depender de frameworks
- Contiene reglas de negocio

---

## 2.4 Infrastructure Layer (Persistencia / Integraciones)

### Propósito
Adaptar el dominio a tecnologías externas (DB, APIs, etc.)

### Convenciones

- Base de datos: `*Data`
- Mongo: `*Document`
- SQL/Records: `*Record`

### Ejemplo

class UserData:
    id: str
    email: str

### Reglas

- Representa estructuras externas (no dominio)
- Puede tener anotaciones (JPA, etc.)
- Se transforma hacia/desde el dominio

---

# 3. Mappers (Transformaciones)

### Propósito
Convertir entre capas sin acoplarlas.

### Ejemplos

CreateUserRequest -> CreateUserCommand
UserData -> User
User -> UserResponse

### Reglas

- SIEMPRE usar mappers entre capas
- NO mezclar modelos directamente
- Mantener desacoplamiento

---

# 4. Flujo Completo

Controller
↓
Request
↓
Command
↓
UseCase
↓
Domain
↓
Repository
↓
Data
↓
Mapper
↓
Response

---

# 5. Reglas Generales

## Permitido

- Separar modelos por capa
- Usar nombres semánticos (Command, Request, etc.)
- Mapear entre capas

## Prohibido

- Usar `Request` en dominio
- Usar `Entity` JPA como modelo de negocio
- Mezclar DTOs con lógica
- Saltarse la capa de Application

---

# 6. Guía Rápida

| Capa           | Nombre        | Ejemplo               |
|----------------|--------------|----------------------|
| API            | Request      | CreateUserRequest    |
| API            | Response     | UserResponse         |
| Application    | Command      | CreateUserCommand    |
| Application    | Result       | CreateUserResult     |
| Domain         | Entidad      | User                 |
| Domain         | Value Object | Email                |
| Infrastructure | Data         | UserData             |

---

# 7. Convención Recomendada

- API → `Request / Response`
- Application → `Command / Result`
- Domain → sin sufijos
- Infrastructure → `Data`

---

# 8. Nota Final

El objetivo de estas convenciones es:

- Reducir acoplamiento
- Mejorar mantenibilidad
- Hacer explícita la intención del código
- Facilitar el trabajo de agentes (IA) y desarrolladores
