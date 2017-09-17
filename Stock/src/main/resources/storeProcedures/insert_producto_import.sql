CREATE PROCEDURE stock.insert_producto_import(
IN in_nombre CHAR(200),
IN in_existencia INT,
IN in_precio_compra FLOAT,
IN in_precio_venta FLOAT,
IN in_marca CHAR(200),
IN in_ubicacion CHAR(200),
IN in_unidad CHAR(200),
IN in_categoria CHAR(200),
IN in_codigo_barras CHAR(200),
IN in_minimo INT,
IN in_descripcion CHAR(200),
IN in_naturaleza CHAR(200),
IN in_organizacion BIGINT,
IN in_usuario BIGINT,
IN in_actualizacion DATE)
	FLAG: BEGIN
		DECLARE id_table BIGINT;
		DECLARE id_marca_found BIGINT;
		DECLARE id_categoria_found BIGINT;
		DECLARE id_naturaleza_found BIGINT;
		DECLARE id_ubicacion_found BIGINT;
		DECLARE id_unidad_found BIGINT;
		
		
		IF(in_codigo_barras = 'null') THEN
			SET in_codigo_barras = NULL;
		END IF;
		IF(in_descripcion = 'null') THEN
			SET in_descripcion = NULL;
		END IF;
		IF(in_marca = 'null') THEN
			SET in_marca = NULL;
		END IF;
		IF(in_minimo = 0) THEN
			SET in_minimo = NULL;
		END IF;
		IF(in_precio_compra = 0) THEN
			SET in_precio_compra = NULL;
		END IF;
		
	
		SELECT idproducto INTO id_table FROM Producto
		WHERE nombre = in_nombre;
		
		CALL stock.find_marca_by_nombre(in_marca, in_usuario, id_marca_found);
		CALL stock.find_categoria_by_nombre(in_categoria, in_usuario, id_categoria_found);
		CALL stock.find_naturaleza_by_nombre(in_naturaleza, in_usuario, id_naturaleza_found);
		CALL stock.find_ubicacion_by_nombre(in_ubicacion, id_ubicacion_found);
		CALL stock.find_unidad_by_nombre(in_unidad, in_organizacion, in_usuario, id_unidad_found);
		
		IF (id_table IS NULL) THEN
			INSERT INTO stock.Producto(	
				activo, categoria, clave, codigoBarras, descripcion,
				enExistencia, imagen, marca, maximo, minimo,
				modelo, nombre, precioCompra, precioVenta,
				lugar, organizacion, productoNaturaleza, unidad, usuario, fechaActualizacion)
			VALUES (1, id_categoria_found, NULL, in_codigo_barras, in_descripcion,
					in_existencia, NULL, id_marca_found, NULL, in_minimo,
					NULL, in_nombre, in_precio_compra, in_precio_venta, 
					id_ubicacion_found, in_organizacion, id_naturaleza_found,
					id_unidad_found, in_usuario, in_actualizacion);

		ELSE
			UPDATE stock.Producto 
				SET categoria = id_categoria_found,
				codigoBarras = in_codigo_barras,
				descripcion = in_descripcion,
				enExistencia = in_existencia, 
				marca = id_marca_found, 
				minimo = in_minimo, 
				productoNaturaleza = id_naturaleza_found,
				nombre = in_nombre,
				precioCompra = in_precio_compra,
				precioVenta = in_precio_venta,
				lugar = id_ubicacion_found,
				unidad = id_unidad_found,
				fechaActualizacion = in_actualizacion
				WHERE (idProducto = id_table);

		END IF;
	END FLAG