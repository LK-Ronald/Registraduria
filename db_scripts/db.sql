--- CREACION DE LOS ROLES PARA LA SEGURIDAD

-- crearemos el dueño de esquemas y tablas
CREATE ROLE empresa_owner NOLOGIN;

CREATE DATABASE empresa;

CREATE SCHEMA IF NOT EXISTS logs; -- Crear esquemas
CREATE SCHEMA IF NOT EXISTS acceso; --

--- Agregar extenciones necesarias
CREATE EXTENSION IF NOT EXISTS pgcrypto;

--- Crear la tabla usuarios
CREATE TABLE IF NOT EXISTS acceso.tb_usuarios(
	codigo INT GENERATED ALWAYS AS IDENTITY,
	nid INT NOT NULL CONSTRAINT idx_unique_nid UNIQUE,
	nombre VARCHAR(30) NOT NULL,
	apellido VARCHAR(30) NOT NULL,
	correo VARCHAR(50) NOT NULL CONSTRAINT idx_unique_correo UNIQUE, --- Para evitar correos duplicados
	clave TEXT NOT NULL,
	fecha_naci DATE NOT NULL, -- Agregar un indice si es necesario
	fecha_regis TIMESTAMP NOT NULL, -- Agregar un indice si es necesario
	CONSTRAINT pk_usuarios_codigo PRIMARY KEY(codigo)
);

--- CREAR LA TABLA DE LOGS
CREATE TABLE IF NOT EXISTS logs.tb_registros_cambios(
	id INT GENERATED ALWAYS AS IDENTITY,
	nid INT NOT NULL,
	nombre VARCHAR(30) NOT NULL,
	apellido VARCHAR(30) NOT NULL,
	correo VARCHAR(50) NOT NULL,
	clave TEXT NOT NULL,
	cambio VARCHAR(60) NOT NULL,
	fecha_cambio TIMESTAMP NOT NULL, -- AGREGAR INDICE SI ES NECESARIO
	CONSTRAINT pk_id_registro_cambios PRIMARY KEY(id)
);

--- TRIGGER QUE REGISTRA LOS CAMBIOS DE CLAVE
CREATE OR REPLACE FUNCTION logs.fc_cambio_clave()
RETURNS TRIGGER AS $$
BEGIN
	--- INSERTA EN LA TABLA logs.tb_registros_cambios CUANDO SE CAMBIA LA CLAVE
	INSERT INTO logs.tb_registros_cambios(nid, nombre, apellido, correo, clave, fecha_cambio, cambio)
		VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, OLD.clave, CURRENT_TIMESTAMP, 'Se cambio la clave de acceso');
	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tr_after_cambios
AFTER UPDATE ON acceso.tb_usuarios
FOR EACH ROW
WHEN (OLD.clave IS DISTINCT FROM NEW.clave)
EXECUTE FUNCTION logs.fc_cambio_clave();


--- TRIGGER PARA REGISTRAR LOS CAMBIOS DE CORREO
CREATE OR REPLACE FUNCTION logs.fc_cambio_correo()
RETURNS TRIGGER AS $$
BEGIN
	--- CUANDO SE CAMBIE EL CORREO SE INSERTARA EL CAMBIO ECHO EN LA TABLA logs.tb_registros_cambios
	INSERT INTO logs.tb_registros_cambios(nid, nombre, apellido, correo, clave, fecha_cambio, cambio)
		VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, OLD.clave, CURRENT_TIMESTAMP, 'Se cambio el correo');
	
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tr_after_cambio_correo
AFTER UPDATE ON acceso.tb_usuarios
FOR EACH ROW
WHEN (OLD.correo IS DISTINCT FROM NEW.correo)
EXECUTE FUNCTION logs.fc_cambio_correo();

--- TRIGGER PARA REGISTRAR LOS USUARIOS BORRADOS/ELIMINADOS DE acceso.tb_usuarios
CREATE OR REPLACE FUNCTION logs.fc_eliminado_usuario()
RETURNS TRIGGER AS $$
BEGIN
	--- CUANDO SE ELIMINA UN USUARIO DE LA TABLA acceso.tb_usuarios se registra en logs.tb_registros_cambios
	INSERT INTO logs.tb_registros_cambios(nid, nombre, apellido, correo, clave, fecha_cambio, cambio)
		VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, OLD.clave, CURRENT_TIMESTAMP, 'Se elimino el usuario');
		
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tr_after_eliminar_usuario
AFTER DELETE ON acceso.tb_usuarios
FOR EACH ROW
EXECUTE FUNCTION logs.fc_eliminado_usuario();


--- ASIGNACION DE DUENOS 
ALTER SCHEMA acceso OWNER TO empresa_owner;
ALTER SCHEMA logs OWNER TO empresa_owner;
ALTER TABLE acceso.tb_usuarios OWNER TO empresa_owner;
ALTER TABLE logs.tb_registros_cambios OWNER TO empresa_owner;

---Insert de prueba
INSERT INTO acceso.tb_usuarios(nid, nombre, apellido, correo, clave, fecha_regis, fecha_naci) 
	VALUES(12345,'Ronald', 'David', 'ronalddavid@gmail.com', 'mi_clave',CURRENT_TIMESTAMP, NOW());


