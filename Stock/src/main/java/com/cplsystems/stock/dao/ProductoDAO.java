package com.cplsystems.stock.dao;

import com.cplsystems.stock.domain.Producto;

import java.util.List;

public abstract interface ProductoDAO {
	public abstract void save(Producto producto);

	public abstract void delete(Producto producto);

	public abstract Producto getById(Long idProducto);

	public abstract List<Producto> getAll();

	public abstract List<Producto> getByClaveNombre(String paramString);

	public abstract List<Producto> getByPrecio(String paramString);

	public abstract Producto getByClave(String paramString);

	public void saveImportProducto(Producto producto);

	public String getNewCodeBar();

	public abstract List<Producto> getAllActivos();

	public abstract Producto getByCodigoBarras(String codigoBarras);
	public abstract void crearProcedimientos();
}
