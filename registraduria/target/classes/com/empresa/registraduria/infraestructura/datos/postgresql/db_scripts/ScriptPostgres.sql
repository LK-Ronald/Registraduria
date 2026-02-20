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
	activo BOOLEAN DEFAULT TRUE NOT NULL, -- Mientras la persona este activa con valor por defecto 'true'
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
	fecha_cambio TIMESTAMP NOT NULL, -- AGREGAR INDICE SI ES NECESARIO
	activo BOOLEAN NOT NULL,
	cambio VARCHAR(60) NOT NULL,
	CONSTRAINT pk_id_registro_cambios PRIMARY KEY(id)
);

--- UN SOLO TRIGGER PARA AUDITAR LOS CAMBIOS EN LA TABLA USUARIOS

CREATE OR REPLACE FUNCTION logs.fc_auditoria_usuarios()
RETURNS TRIGGER AS $$
DECLARE
	v_mensaje VARCHAR;
BEGIN
	-- EVALUAR LOS VALORES
	IF NEW.clave IS DISTINCT FROM OLD.clave THEN
		v_mensaje := 'Se cambio la clave de acceso';
	ELSIF NEW.correo IS DISTINCT FROM OLD.correo THEN
		v_mensaje := 'Se cambio el correo';
	ELSIF OLD.activo IS TRUE AND NEW.activo IS FALSE THEN
	    v_mensaje := 'Usuario desactivado';
	ELSIF OLD.activo IS FALSE AND NEW.activo IS TRUE THEN
	    v_mensaje := 'Usuario reactivado';
	ELSE
	
		RETURN NEW;
	END IF;
	
	INSERT INTO logs.tb_registros_cambios(nid, nombre, apellido, correo, clave, fecha_cambio, activo, cambio)
		VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, OLD.clave, CURRENT_TIMESTAMP, OLD.activo, v_mensaje);
		
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tr_auditoria_usuarios
AFTER UPDATE ON acceso.tb_usuarios
FOR EACH ROW
WHEN (
	OLD.clave IS DISTINCT FROM NEW.clave OR 
	OLD.correo IS DISTINCT FROM NEW.correo OR 
	NEW.activo IS DISTINCT FROM OLD.activo
)
EXECUTE FUNCTION logs.fc_auditoria_usuarios();

--- ASIGNACION DE DUENOS 
ALTER SCHEMA acceso OWNER TO empresa_owner;
ALTER SCHEMA logs OWNER TO empresa_owner;
ALTER TABLE acceso.tb_usuarios OWNER TO empresa_owner;
ALTER TABLE logs.tb_registros_cambios OWNER TO empresa_owner;
