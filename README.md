# MiniProyecto - Sistema de Gestion de Registraduria

Este proyecto es una aplicacion de consola desarrollada en Java que gestiona una base de datos de personas para una entidad de registraduria. Utiliza PostgreSQL como sistema de gestion de bases de datos.

## Tecnologias Utilizadas

- Lenguaje de Programacion: Java
- Sistema de Gestion de Base de Datos (SGDB): PostgreSQL, MySQL
- Herramientas de Construccion y Gestion de Dependencias: Maven (implícito en la estructura del proyecto)

## Estructura del Proyecto

El proyecto sigue una arquitectura organizada para separar las responsabilidades, facilitando el mantenimiento y la escalabilidad. A continuacion se detalla la estructura principal de paquetes:

- **com.empresa.registraduria.app**
  - Contiene la clase `Main.java`, que es el punto de entrada de la aplicacion y maneja la interaccion con el usuario a traves de un menu en consola.

- **com.empresa.registraduria.dominio**
  - Nucleo de la logica de negocio.
  - **modelo**: Define las entidades del dominio, como la clase `Persona`.
  - **puerto**: Interfaces que definen los contratos para los servicios y repositorios, como `IRegistroPersonas`.
  - **servicio**: Implementaciones concretas de la logica de negocio, como `RegistroPersonasImpl`.
  - **exepciones**: Excepciones personalizadas para el manejo de errores especificos del dominio.

- **com.empresa.registraduria.infraestructura**
  - Capa encargada de la persistencia y acceso a datos.
  - **datos**: Implementaciones para el acceso a la base de datos (PostgreSQL, MySQL, Archivos), gestionada a traves de interfaces como `IAccesoDatos`.

- **com.empresa.registraduria.util**
  - Clases de utilidad transversal, como `FechaActu` para manejo de fechas y `HashPassword` para seguridad.

## Configuracion de la Base de Datos

Para el correcto funcionamiento del sistema, es necesario configurar la base de datos.
> [!NOTE]
> **Versión 2.0.0**: A partir de la versión 2.0.0, el sistema utiliza **MySQL** por defecto.

### MySQL (Por defecto)
1. **Script de Inicializacion**: El archivo SQL con las sentencias necesarias para crear la estructura de la base de datos se encuentra en `registraduria/src/main/java/com/empresa/registraduria/infraestructura/datos/mysql/scripts/empresa.sql`.
2. **Ejecucion**: Puede ejecutar este script directamente en su servidor MySQL o utilizar la opcion correspondiente desde el menu de la aplicacion.

### PostgreSQL (Opcional)
1. **Script de Inicializacion**: `db_scripts/db.sql`.
2. **Cambiar Motor de Base de Datos**: Para usar PostgreSQL, debe modificar la clase `Main.java` y descomentar la línea que instancia `AccesoDatosImpl` (PostgreSQL) y comentar `AccesoDatosMysqlImpl` (MySQL).

## Manual de Usuario

Al iniciar la aplicacion, se presentara un menu con las siguientes opciones para gestionar el registro de personas:

1. **Crear base de datos**: Ejecuta el script de configuracion inicial para preparar la base de datos.
2. **Agregar persona**: Permite registrar una nueva persona ingresando su NID, nombre, apellido, correo, clave y fecha de nacimiento.
3. **Buscar persona**: Busca y muestra la informacion de una persona especificando su numero de identificacion (NID).
4. **Listar personas**: Muestra una lista completa de todas las personas registradas en el sistema.
5. **Borrar persona**: Elimina el registro de una persona dado su NID.
6. **Actualizar clave**: Permite cambiar la contrasena de una persona registrada.
7. **Existe persona**: Verifica si un NID especifico ya se encuentra registrado en el sistema.
8. **Salir**: Cierra la aplicacion.

## Notas Tecnicas

- **Seguridad**: Las contrasenas de los usuarios son almacenadas de forma segura utilizando algoritmos de hashing.
- **Manejo de Fechas**: El sistema utiliza `LocalDate` para el manejo preciso de fechas de nacimiento.
- **Arquitectura**: El diseno desacopla la logica de negocio de la implementacion de base de datos, permit
iendo cambiar el motor de persistencia con un impacto minimo en el codigo del dominio.
