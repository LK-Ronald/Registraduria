CREATE DATABASE IF NOT EXISTS empresa;

USE empresa;

CREATE TABLE IF NOT EXISTS empresa.usuarios(
	codigo INT UNSIGNED AUTO_INCREMENT,
    nid INT UNSIGNED NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    clave TEXT NOT NULL,
    fecha_naci DATE NOT NULL, -- Agregar indice si es necesario
    fecha_regis TIMESTAMP NOT NULL, -- Agregar indice si es necesario
    CONSTRAINT pk_usuarios_codigo PRIMARY KEY(codigo),
    CONSTRAINT idx_unique_correo UNIQUE (correo) -- Para evitar correos duplicados
);

CREATE TABLE IF NOT EXISTS empresa.logs_registros(
	id INT AUTO_INCREMENT,
	nid INT NOT NULL,
	nombre VARCHAR(30) NOT NULL,
	apellido VARCHAR(30) NOT NULL,
	correo VARCHAR(50) NOT NULL,
	clave TEXT NOT NULL,
	cambio VARCHAR(60) NOT NULL,
	fecha_cambio TIMESTAMP NOT NULL, -- AGREGAR INDICE SI ES NECESARIO
	CONSTRAINT pk_id_registro_cambios PRIMARY KEY(id)
);

-- Cuando se cambia la clave se guarda en la tabla logs_cambios o cuando se cambie el correo
DELIMITER $$
CREATE TRIGGER after_update_usuario_audit
AFTER UPDATE ON empresa.usuarios
FOR EACH ROW
BEGIN
	-- Detectar cambio de clave
    IF OLD.clave != NEW.clave THEN
        INSERT INTO empresa.logs_registros(nid, nombre, apellido, correo, clave, cambio, fecha_cambio)
			VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, NEW.clave, 'Se cambió la clave', NOW());
    END IF;

    -- Detectar cambio de correo
    IF OLD.correo != NEW.correo THEN
        INSERT INTO empresa.logs_registros(nid, nombre, apellido, correo, clave, cambio, fecha_cambio)
			VALUES (OLD.nid, OLD.nombre, OLD.apellido, NEW.correo, OLD.clave, 'Se cambió el correo', NOW());
    END IF;
END; $$
DELIMITER ;