CREATE PROCEDURE find_categoria_by_nombre(
IN in_nombre CHAR(200),
IN id_usuario BIGINT,
OUT id_categoria_found BIGINT)
	FLAG: BEGIN
		IF (in_nombre <> 'null') THEN
			SELECT idCategoria INTO id_categoria_found FROM Categoria
			WHERE nombre = in_nombre;
			
			IF (id_categoria_found IS NULL) THEN
				INSERT INTO Categoria (descripcion, fechaActualizacion, nombre, usuario)
				VALUES (NULL, NOW(), in_nombre, id_usuario);
				
				SELECT idCategoria INTO id_categoria_found FROM Categoria
				WHERE nombre = in_nombre
				AND usuario = id_usuario;
			END IF;
		END IF;
END FLAG