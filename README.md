# MindFocus API 🧠

API robusta para la gestión de hábitos y análisis de atención, desarrollada con **Spring Boot** bajo **Arquitectura Hexagonal**. Este proyecto demuestra habilidades avanzadas en diseño de bases de datos y principios de ingeniería de software.

## 🚀 Características

- **Gestión de hábitos** — CRUD completo con categorización dinámica.
- **Sesiones de productividad** — Registro de métricas de enfoque (focus score, distracciones, duración).
- **Categorías de actividad** — `WORK`, `STUDY`, `LEISURE`, `SOCIAL_MEDIA`.
- **Historial de cambios** — Auditoría completa con integridad referencial.
- **Documentación interactiva** — Swagger UI disponible en `/swagger-ui.html`.
- **Validación de datos** — Bean Validation en todos los endpoints.
- **Manejo global de errores** — Respuestas estructuradas para todos los casos de error.

## 🏗️ Arquitectura Hexagonal

```
src/main/java/com/mindfocus/api/
├── domain/                         ← Núcleo de negocio (sin dependencias externas)
│   ├── model/                      ← Entidades de dominio (Habit, Session, HabitHistory)
│   └── port/
│       ├── in/                     ← Puertos de entrada (casos de uso)
│       └── out/                    ← Puertos de salida (repositorios)
├── application/
│   └── service/                    ← Implementaciones de casos de uso
└── infrastructure/
    ├── adapter/
    │   ├── in/web/                 ← Controladores REST (adaptadores de entrada)
    │   └── out/persistence/        ← JPA (adaptadores de salida)
    └── config/                     ← Configuración (Swagger, manejo de errores)
```

## 🛠️ Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.5 | Framework web y DI |
| MySQL | 8+ | Base de datos de producción |
| H2 | — | Base de datos en memoria (testing) |
| Flyway | — | Migraciones de base de datos |
| Lombok | — | Reducción de boilerplate |
| SpringDoc / Swagger | 2.5.0 | Documentación interactiva |
| JUnit 5 + Mockito | — | Tests unitarios |

## 📋 Endpoints

### Hábitos (`/api/v1/habits`)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/v1/habits` | Crear un hábito |
| `GET` | `/api/v1/habits` | Listar todos (con filtro opcional por categoría) |
| `GET` | `/api/v1/habits/{id}` | Obtener un hábito por ID |
| `PUT` | `/api/v1/habits/{id}` | Actualizar un hábito |
| `DELETE` | `/api/v1/habits/{id}` | Eliminar un hábito |
| `GET` | `/api/v1/habits/{id}/history` | Ver historial de cambios de un hábito |
| `GET` | `/api/v1/habits/history` | Ver historial completo |

### Sesiones (`/api/v1/sessions`)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/v1/sessions` | Registrar una sesión de productividad |
| `GET` | `/api/v1/sessions` | Listar sesiones (con filtro opcional por habitId) |
| `GET` | `/api/v1/sessions/{id}` | Obtener una sesión por ID |
| `PUT` | `/api/v1/sessions/{id}` | Actualizar una sesión |
| `DELETE` | `/api/v1/sessions/{id}` | Eliminar una sesión |
| `GET` | `/api/v1/sessions/habits/{habitId}/focus-score` | Promedio de focus score de un hábito |

## 🚦 Cómo ejecutar

### Requisitos previos
- Java 17+
- Maven 3.8+
- MySQL 8+ (en producción)

### Configuración

Copia o configura las variables de entorno para la base de datos:

```bash
export DB_URL=jdbc:mysql://localhost:3306/mindfocus_db?createDatabaseIfNotExist=true
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_password
```

### Ejecución

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.  
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Tests

```bash
mvn test
```

Los tests usan H2 en memoria — no requieren MySQL.

## 🗄️ Modelo de Base de Datos

```sql
habits (id, name, description, category, active, created_at, updated_at)
    └── sessions (id, habit_id FK, start_time, end_time, duration_minutes,
                  focus_score, distractions, notes, created_at)
    └── habit_history (id, habit_id FK, change_type, details, changed_at)
```

Categorías válidas: `WORK`, `STUDY`, `LEISURE`, `SOCIAL_MEDIA`  
Tipos de cambio en historial: `CREATED`, `UPDATED`, `DELETED`
