CREATE PROCEDURE find_ubicacion_by_nombre(
IN in_nombre CHAR(200),
OUT id_ubicacion_found BIGINT)
	FLAG: BEGIN
		IF (in_nombre <> 'null') THEN
			SELECT idLugar INTO id_ubicacion_found FROM Lugar
			WHERE nombre = in_nombre;
			
			IF (id_ubicacion_found IS NULL) THEN
				INSERT INTO Lugar (nombre, proyecto) VALUES (in_nombre, NULL);
				SELECT idLugar INTO id_ubicacion_found FROM Lugar
				WHERE nombre = in_nombre;
			END IF;
		END IF;
END FLAG