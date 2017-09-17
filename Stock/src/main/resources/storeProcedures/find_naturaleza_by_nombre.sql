CREATE PROCEDURE find_naturaleza_by_nombre(IN in_nombre CHAR(200), IN id_usuario BIGINT, OUT id_naturaleza_found BIGINT)
	BEGIN
		IF (in_nombre <> 'null') THEN
			SELECT idProductoNaturaleza INTO id_naturaleza_found FROM ProductoNaturaleza
			WHERE nombre = in_nombre;
			
			IF (id_naturaleza_found IS NULL) THEN
				INSERT INTO ProductoNaturaleza (nombre, simbolo) VALUES (in_nombre, NULL);
				
				SELECT idProductoNaturaleza INTO id_naturaleza_found FROM ProductoNaturaleza
				WHERE nombre = in_nombre;
			END IF;
		END IF;
	END