# MiniProyecto - Sistema de Gestión de Registraduría (v4.0.0)

Este es un sistema de gestión de personas desarrollado en **Java**, diseñado bajo principios de **Arquitectura Limpia (Hexagonal)** y utilizando **JPA con Hibernate** para una persistencia robusta y flexible. El sistema soporta tanto **MySQL** como **PostgreSQL**.

## 🚀 Tecnologías y Herramientas

- **Java 21**: Lenguaje principal.
- **Jakarta Persistence API (JPA) 4.0**: Estándar de persistencia.
- **Hibernate 8.0 (Alpha)**: Proveedor de persistencia (ORM).
- **PostgreSQL 42.7 / MySQL 9.6**: Motores de base de datos soportados.
- **Spring Security (Crypto)**: Utilizado para el hasheo seguro de contraseñas (BCrypt).
- **Maven**: Gestión de dependencias y construcción.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una estructura de capas para garantizar el desacoplamiento y la mantenibilidad:

- **`com.empresa.registraduria.dominio`**:
    - **`modelo`**: Contiene la entidad `Persona`, diseñada como un POJO (Plain Old Java Object) puro, sin lógica de infraestructura.
    - **`servicio`**: Capa donde reside la lógica de negocio (ej. hasheo de contraseñas mediante `RegistroPersonasImpl`).
    - **`puerto`**: Interfaces que definen los contratos (`PersonaRepository`).
- **`com.empresa.registraduria.infraestructura`**:
    - **`datos`**: Implementaciones de persistencia (`MysqlPersonaRepository`, `PostgresPersonaRepository`) utilizando JPA.
- **`com.empresa.registraduria.app`**: Punto de entrada con el menú interactivo (`Main.java`).

## ⚙️ Configuración y Despliegue

### 1. Requisitos Previos
- Tener instalado JDK 17 o superior.
- Tener instalado Maven.
- Contar con una instancia de MySQL o PostgreSQL activa.

### 2. Configuración de la Base de Datos
El sistema utiliza el archivo `src/main/resources/META-INF/persistence.xml` para gestionar la conexión.

#### Para usar PostgreSQL (Configuración por defecto en el código):
1. Asegúrate de que el archivo `persistence.xml` tenga activa la sección de PostgreSQL.
2. Ejecuta el script de inicialización: `src/main/java/com/empresa/registraduria/infraestructura/datos/postgresql/db_scripts/ScriptPostgres.sql`.

#### Para usar MySQL:
1. Cambia la configuración en `persistence.xml` para apuntar al driver y URL de MySQL.
2. Ejecuta el script de inicialización: `src/main/java/com/empresa/registraduria/infraestructura/datos/mysql/scripts/ScriptMysql.sql`.
3. En la clase `Main.java`, asegúrate de instanciar `MysqlPersonaRepository`.

> [!IMPORTANT]
> Gracias a Hibernate (`hibernate.hbm2ddl.auto = update`), las tablas se crearán o actualizarán automáticamente al iniciar la aplicación si la base de datos ya existe.

### 3. Ejecución
Para compilar y ejecutar el proyecto desde la terminal:
```bash
mvn compile exec:java -Dexec.mainClass="com.empresa.registraduria.app.Main"
```

## 🛠️ Manual de Usuario

El sistema ofrece las siguientes funcionalidades:
1. **Crear base de datos**: Mensaje informativo sobre la gestión automática de JPA.
2. **Agregar persona**: Registro completo con validación y hasheo de clave.
3. **Buscar persona**: Consulta por NID.
4. **Listar personas**: Visualización de todos los registros activos.
5. **Borrar persona**: Eliminación lógica (marcado como inactivo).
6. **Actualizar clave**: Cambio seguro de contraseña.
7. **Existe persona**: Validación rápida de existencia.

---
