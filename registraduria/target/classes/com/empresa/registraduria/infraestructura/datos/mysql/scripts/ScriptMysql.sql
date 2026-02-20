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
    activo BOOLEAN DEFAULT TRUE NOT NULL, -- 1 Es true, 0 es false
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
	fecha_cambio TIMESTAMP NOT NULL, -- AGREGAR INDICE SI ES NECESARIO
	activo BOOLEAN NOT NULL,
	cambio VARCHAR(60) NOT NULL,
	CONSTRAINT pk_id_registro_cambios PRIMARY KEY(id)
);

-- Cuando se cambia la clave se guarda en la tabla logs_cambios o cuando se cambie el correo
DELIMITER $$
CREATE TRIGGER after_update_usuario_audit
AFTER UPDATE ON empresa.usuarios
FOR EACH ROW
BEGIN
	DECLARE v_mensaje VARCHAR(60);
    SET v_mensaje = null;
	
	IF OLD.clave != NEW.clave THEN
        SET v_mensaje = 'Se cambio la clave';
    ELSEIF OLD.correo != NEW.correo THEN
        SET v_mensaje = 'Se cambio el correo';
    -- Solo auditar si el estado REALMENTE cambió
    ELSEIF OLD.activo != NEW.activo THEN
        IF NEW.activo = FALSE THEN
            SET v_mensaje = 'Usuario desactivado';
        ELSE
            SET v_mensaje = 'Usuario reactivado';
        END IF;
    END IF;
    
    -- Insertar si se cumplio alguna condicion
    IF v_mensaje IS NOT NULL THEN
    	INSERT INTO empresa.logs_registros(nid, nombre, apellido, correo, clave, fecha_cambio, activo, cambio)
			VALUES (OLD.nid, OLD.nombre, OLD.apellido, OLD.correo, OLD.clave, NOW(), OLD.activo, v_mensaje);
	END IF;
END; $$
DELIMITER ;