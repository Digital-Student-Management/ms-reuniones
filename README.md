# 📝 Microservicio de Reuniones y Bitácoras (ms-reuniones)

Este repositorio contiene el código fuente del **Microservicio de Gestión de Reuniones, Actas y Bitácoras**, componente fundamental de la arquitectura distribuida del Sistema Integral de Gestión Estudiantil Digital para el **Colegio Bernardo O'Higgins de Coquimbo**.

Este módulo centraliza el registro operativo de todas las actividades grupales e institucionales del establecimiento, divididas entre las actas administrativas de la directiva y el seguimiento conductual/académico que los docentes realizan con los apoderados.

---

## 🚀 Descripción de la Arquitectura y Dominio

El `ms-reuniones` está diseñado bajo un enfoque de **Bajo Acoplamiento** y **Especialización de Datos**. No comparte base de datos con otros servicios, sino que se enlaza de forma lógica mediante **Referencias Suaves (Soft References)** a través de peticiones HTTP (usando `RestTemplate`) hacia el núcleo de identidades (`ms-usuarios`).

### 🧬 Modelo de Datos y Herencia Polimórfica (JPA Joined)
Para representar fielmente el diagrama MERE sin redundancia de datos, el módulo implementa una estrategia de herencia compartida `@Inheritance(strategy = InheritanceType.JOINED)` a nivel de ORM (Hibernate).

* **Tabla Padre Principal (`bitacora_reuniones_apo`):** Almacena los campos comunes a cualquier reunión del cuerpo docente (Fecha programada, acuerdos, estado e ID del docente a cargo).
* **Tabla Hija 1 (`bitacora_citacion_apoderado`):** Extiende de la principal y guarda los datos de entrevistas individuales (Tema específico, enlace al estudiante y al apoderado).
* **Tabla Hija 2 (`bitacora_reunion_general`):** Extiende de la principal y guarda la bitácora de asambleas de curso (Temario general e ID del curso correspondiente).
* **Entidad Independiente (`acta_reunion_interna`):** Tabla aislada dedicada exclusivamente al registro de consejos de coordinación, bitácoras de dirección y acuerdos del equipo directivo.

---

## 🛠️ Stack Tecnológico

* **Lenguaje de Programación:** Java 21 (LTS)
* **Framework Core:** Spring Boot 4.0.6
* **Gestor de Dependencias:** Maven
* **Base de Datos Relacional:** MySQL 8.x
* **Mapeo Objeto-Relacional (ORM):** Spring Data JPA / Hibernate 7.x
* **Documentación Interactiva:** SpringDoc OpenAPI 3.0.2 (Swagger UI)
* **Utilidades:** Lombok (Getters, Setters, Builders automáticos)
* **Validación de Datos:** Jakarta Validation

---

## 📁 Estructura del Proyecto

La organización del código sigue el patrón arquitectónico limpio segmentado por capas operativas y dominios funcionales:

```text
ms-reuniones/
├── peticiones.rest                          ← Archivo de pruebas rápidas (REST Client)
├── pom.xml                                  ← Definición de dependencias Maven
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── ms_reuniones/
        │           ├── MsReunionesApplication.java   ← Clase principal de arranque
        │           ├── config/                       ← Configuraciones de red y seguridad
        │           │   ├── RestTemplateConfig.java   ← Cliente HTTP para validación cruzada
        │           │   └── WebConfig.java            ← Reglas de CORS globales
        │           ├── controller/                   
        │           │   └── ReunionesController.java  ← Endpoints unificados de la API REST
        │           ├── models/
        │           │   ├── dto/                      ← Clases de transferencia de datos
        │           │   │   ├── ActaRequestDTO.java
        │           │   │   ├── ActaResponseDTO.java
        │           │   │   ├── CitacionRequestDTO.java
        │           │   │   └── ReunionGeneralRequestDTO.java
        │           │   └── entities/                 ← Capa de persistencia (Base de Datos)
        │           │       ├── ActaReunionInterna.java
        │           │       ├── BitacoraReunionApo.java
        │           │       ├── CitacionApoderado.java
        │           │       └── ReunionGeneral.java
        │           ├── repositories/                 ← Abstracción de consultas JPA
        │           │   ├── ActaReunionInternaRepository.java
        │           │   ├── CitacionApoderadoRepository.java
        │           │   └── ReunionGeneralRepository.java
        │           └── services/                     ← Capa de lógica de negocio y validación
        │               ├── ActaService.java
        │               └── BitacoraService.java
        └── resources/
            └── application.properties                ← Variables de entorno y puertos
```

---

## 🛡️ Mecanismo de Validación Cruzada (Integridad de Dominio)

Para asegurar la robustez de los datos y evitar incongruencias operativas (como asignar a un estudiante como líder de un consejo directivo), los servicios `ActaService` y `BitacoraService` realizan inspecciones en tiempo real mediante `RestTemplate` al endpoint `GET http://localhost:8080/api/usuarios/{id}` del microservicio base.

Aprovechando el DTO polimórfico implementado por el equipo (`tipoUsuario`), el sistema descifra el JSON y valida estrictamente las siguientes reglas de negocio antes de confirmar cualquier inserción en la base de datos:
* Una **Acta Interna** solo se guarda si el ID de origen pertenece de verdad a un `DIRECTIVO`.
* Una **Citación Individual** requiere verificar de forma independiente que el creador sea un `DOCENTE`, el alumno un `ESTUDIANTE` y el citado un `APODERADO`.
* Una **Reunión General** restringe su campo de jefatura exclusivamente a usuarios catalogados como `DOCENTE`.

---

## ⚙️ Instalación y Despliegue Local

### 1. Clonar y Configurar Entorno
Asegúrate de contar con el puerto **8085** libre en tu máquina local.

```bash
git clone [https://github.com/TuOrganizacion/ms-reuniones.git](https://github.com/TuOrganizacion/ms-reuniones.git)
cd ms-reuniones
```

### 2. Variables del Sistema (`.vscode/launch.json`)
El microservicio se nutre de variables de entorno para evitar dejar credenciales expuestas en repositorios públicos. Crea o edita tu archivo de lanzamiento local en VS Code:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Arrancar MS Reuniones",
            "request": "launch",
            "mainClass": "com.ms_reuniones.MsReunionesApplication",
            "projectName": "ms-reuniones",
            "env": {
                "DB_URL": "jdbc:mysql://localhost:3306/reuniones_db?createDatabaseIfNotExist=true&serverTimezone=UTC",
                "DB_USER": "root",
                "DB_PASSWORD": "tu_contraseña_local_aquí"
            }
        }
    ]
}
```

### 3. Ejecutar la Aplicación
Lanza el sistema desde el botón de Play en la pestaña de Depuración de VS Code, o mediante la terminal incorporada utilizando Maven Wrapper:

```bash
./mvnw spring-boot:run
```

---

## 🔌 Documentación y Catálogo de Endpoints (API REST)

Una vez iniciado el servidor, puedes ingresar a la interfaz interactiva de Swagger UI para revisar la especificación técnica completa y probar los controladores en tiempo real desde el navegador:

`http://localhost:8085/swagger-ui.html`

### Resumen de Rutas Base Disponibles

| Método HTTP | Ruta Completa (Endpoint) | Descripción Funcional |
|---|---|---|
| `POST` | `/api/reuniones/actas` | Registra una nueva acta administrativa del equipo directivo. |
| `GET` | `/api/reuniones/actas` | Lista la totalidad de actas históricas del colegio. |
| `GET` | `/api/reuniones/actas/{id}` | Recupera la información detallada de un acta por su ID. |
| `GET` | `/api/reuniones/actas/directivo/{id}` | Filtra y devuelve las actas creadas por un directivo específico. |
| `PUT` | `/api/reuniones/actas/{id}` | Modifica los acuerdos o tipo de una reunión de gestión existente. |
| `DELETE` | `/api/reuniones/actas/{id}` | Elimina físicamente un acta del historial. |
| `POST` | `/api/reuniones/bitacoras/citaciones` | Genera una citación de entrevista para un apoderado individual. |
| `GET` | `/api/reuniones/bitacoras/citaciones` | Obtiene el listado de todas las citaciones agendadas. |
| `PUT` | `/api/reuniones/bitacoras/citaciones/{id}` | Actualiza el estado (PROGRAMADA/REALIZADA) o los compromisos de una citación. |
| `POST` | `/api/reuniones/bitacoras/generales` | Crea la bitácora para una reunión general de padres y apoderados por curso. |
| `GET` | `/api/reuniones/bitacoras/generales` | Lista todas las reuniones de curso registradas. |

---

## 🧪 Pruebas con la Extensión REST Client

Para facilitar las pruebas rápidas de desarrollo sin recurrir a plataformas externas como Postman, se incluye el archivo `peticiones.rest` en la raíz de este proyecto.

1. Instala la extensión **REST Client** de *Huachao Mao* desde el Marketplace de VS Code.
2. Abre el archivo `peticiones.rest`.
3. Haz clic en el botón flotante **`Send Request`** que aparece de manera automática arriba de cada método HTTP.
4. Inspecciona la cabecera de estado y el cuerpo JSON resultante en el panel derecho dividido.