CREATE PROCEDURE find_unidad_by_nombre(
IN in_nombre CHAR(200),
IN in_organizacion BIGINT,
IN in_usuario BIGINT,
OUT id_unidad_found BIGINT)
	FLAG: BEGIN
		IF (in_nombre <> 'null') THEN
			SELECT idUnidad INTO id_unidad_found FROM Unidad
			WHERE nombre = in_nombre;
			
			IF (id_unidad_found IS NULL) THEN
				INSERT INTO Unidad(abreviatura, fechaActualizacion, nombre, organizacion, usuario) 
				VALUES (NULL, NOW(), in_nombre, in_organizacion, in_usuario);
				
				SELECT idUnidad INTO id_unidad_found FROM Unidad
				WHERE nombre = in_nombre;
			END IF;
		END IF;
END FLAG