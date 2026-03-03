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
El sistema utiliza el archivo `src/main/resources/META-INF/persistence.xml` para gestionar la conexión. Este archivo centraliza la configuración del proveedor (Hibernate) y las fuentes de datos.

#### Estructura del `persistence.xml`
Debes configurar tus credenciales en las propiedades correspondientes. A continuación se muestra una plantilla del archivo:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
    <persistence-unit name="MiEmpresa" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>com.empresa.registraduria.dominio.modelo.Persona</class>

        <properties>
            <!-- URL de la base de datos (Ejemplo para PostgreSQL o MySQL) -->
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/tu_base_de_datos" />
            
            <!-- CONFIGURACIÓN DE CREDENCIALES (Cambia estos valores) -->
            <property name="jakarta.persistence.jdbc.user" value="TU_USUARIO" />
            <property name="jakarta.persistence.jdbc.password" value="TU_CONTRASEÑA" />

            <!-- Configuración de Hibernate -->
            <property name="hibernate.hbm2ddl.auto" value="update" />
            <property name="hibernate.show_sql" value="false" />
            <property name="hibernate.format_sql" value="false" />
        </properties>
    </persistence-unit>
</persistence>
```

#### Para usar PostgreSQL (Configuración sugerida):
1. Asegúrate de tener el driver de PostgreSQL activo en tu `pom.xml`.
2. En `persistence.xml`, coloca el host, puerto y nombre de tu base de datos en la propiedad `jdbc.url`.
3. Coloca tu **usuario** y **contraseña** en las propiedades indicadas arriba.

#### Para usar MySQL:
1. Cambia la `jdbc.url` por: `jdbc:mysql://localhost:3306/tu_base_de_datos`.
2. Actualiza tu **usuario** y **contraseña**.
3. Asegúrate de que `MysqlPersonaRepository` esté siendo utilizado en la clase `Main.java`.

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
