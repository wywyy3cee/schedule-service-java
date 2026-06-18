# School Schedule API

REST API for managing teachers, classes, and schedules in a school system.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Lombok

## Prerequisites
- Docker and Docker Compose (recommended), or
- Java 17+, Maven 3.8+, PostgreSQL instance

## Running with Docker

The application and a PostgreSQL instance are defined as services in `docker-compose.yml`.

```bash
docker-compose up --build
```

This builds the application image, starts both containers, and waits for the database to be ready before starting the application. The server is available at `http://localhost:8080`.

Default credentials and database name (overridable via environment variables `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`):

| Variable | Default |
|----------|---------|
| DB_NAME | school_db |
| DB_USER | postgres |
| DB_PASSWORD | postgres |

Tables are created automatically on first run via `ddl-auto=update`.

## Running Manually

Configure the connection in `src/main/resources/application.properties` or via the environment variables listed above, then run:

```bash
mvn spring-boot:run
```

The server starts on `http://localhost:8080`.

API documentation is available at `http://localhost:8080/swagger-ui/index.html`.

---

## API Reference

### Teachers

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/teachers` | Get all teachers |
| GET | `/api/teachers?subject={subject}` | Filter teachers by subject |
| POST | `/api/teachers` | Create a teacher |
| PUT | `/api/teachers/{id}` | Update a teacher |
| DELETE | `/api/teachers/{id}` | Delete a teacher |

**GET /api/teachers?subject=Математика**
```json
[
  {
    "id": 1,
    "firstName": "Иван",
    "lastName": "Иванов",
    "middleName": "Иванович",
    "subject": "Математика"
  }
]
```

**POST /api/teachers**
```json
{
  "firstName": "Иван",
  "lastName": "Иванов",
  "middleName": "Иванович",
  "subject": "Математика"
}
```

---

### Classes

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/classes` | Get all classes |
| POST | `/api/classes` | Create a class |
| PUT | `/api/classes/{id}` | Update a class |
| DELETE | `/api/classes/{id}` | Delete a class |

**POST /api/classes**
```json
{
  "groupNumber": "9А"
}
```

---

### Schedule

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/classes/{id}/schedule?date={date}` | Get schedule for a class on a given date |
| POST | `/api/classes/{id}/schedule` | Add a lesson to a class schedule |

Schedule is determined by the day of week derived from the provided date.

**GET /api/classes/1/schedule?date=2024-01-15**
```json
[
  {
    "id": 1,
    "subject": "Математика",
    "teacherFullName": "Иванов Иван Иванович",
    "startTime": "08:00:00",
    "endTime": "08:45:00",
    "classroom": 101
  }
]
```

**POST /api/classes/1/schedule**
```json
{
  "teacherId": 1,
  "subject": "Математика",
  "dayOfWeek": "MONDAY",
  "startTime": "08:00",
  "endTime": "08:45",
  "classroom": 101
}
```

---

## Error Responses

All errors return a JSON body with the following structure:

```json
{
  "timestamp": "2024-01-15T10:00:00",
  "message": "Error description"
}
```

| Status | Meaning |
|--------|---------|
| 400 | Invalid request body or missing required fields |
| 404 | Resource not found |
| 409 | Conflict — teacher already has a lesson at the requested time |
| 500 | Internal server error |