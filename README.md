# 📝 ms-reuniones — Reuniones, Actas y Citaciones

Microservicio encargado de la bitácora de reuniones del establecimiento. Gestiona tres tipos de
registro:

1. **Actas** de reuniones internas de directivos.
2. **Citaciones** individuales a apoderados.
3. **Reuniones generales** de curso.

> Parte del proyecto GED. Para ejecutar **todo el sistema**, ver el [README raíz](../README.md).

---

## ⚙️ Datos técnicos

| | |
|---|---|
| **Puerto** | `8088` |
| **Base de datos** | `ms_reuniones_db` (MySQL, se crea automáticamente) |
| **Stack** | Spring Boot 4 · Java 21 · Spring Data JPA · RestTemplate · springdoc (Swagger) |

---

## 📡 Endpoints principales — `/api/reuniones`

### Actas (directivos) — `/actas`
`GET /actas` · `GET /actas/{id}` · `GET /actas/directivo/{idDirectivo}` · `POST /actas` · `PUT /actas/{id}` · `DELETE /actas/{id}`

### Citaciones individuales (apoderados) — `/bitacoras/citaciones`
`GET` · `GET /{id}` · `POST` · `PUT /{id}` · `DELETE /{id}`

### Reuniones generales de curso — `/bitacoras/generales`
`GET` · `GET /{id}` · `POST` · `PUT /{id}` · `DELETE /{id}`

---

## ▶️ Ejecución

```bash
mvnw.cmd spring-boot:run     # Windows
./mvnw spring-boot:run       # Linux / macOS
```

- Documentación Swagger: **http://localhost:8088/swagger-ui.html**

---

## 🔗 Relación con otros servicios

Consulta **ms-usuarios** (`:8089`) para validar docentes, apoderados y directivos.
