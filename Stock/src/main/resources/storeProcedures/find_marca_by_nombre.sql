
CREATE PROCEDURE find_marca_by_nombre(
IN in_nombre CHAR(200),
IN id_usuario BIGINT,
OUT id_marca_found BIGINT)
	FLAG: BEGIN
		IF (in_nombre <> 'null') THEN
			SELECT idMarca INTO id_marca_found FROM Marca
			WHERE nombre = in_nombre;
			
			IF (id_marca_found IS NULL) THEN
				INSERT INTO stock.Marca (descripcion, nombre, fechaActualizacion, imagen, usuario)
				VALUES (NULL, in_nombre, NOW(), NULL, id_usuario);
				
				SELECT idMarca INTO id_marca_found FROM Marca
				WHERE nombre = in_nombre
				AND usuario = id_usuario;
			END IF;
		END IF;
END FLAG
