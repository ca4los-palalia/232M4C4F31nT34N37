package com.cplsystems.stock.services;

import com.cplsystems.stock.dao.ProductoDAO;
import com.cplsystems.stock.domain.Producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
	@Autowired
	private ProductoDAO productoDAO;

	public void save(Producto producto) throws DataAccessException {
		productoDAO.save(producto);
	}

	public void delete(Producto producto) throws DataAccessException {
		productoDAO.delete(producto);
	}

	public Producto getById(Long idProducto) throws DataAccessException {
		return productoDAO.getById(idProducto);
	}

	public List<Producto> getAll() throws DataAccessException {
		return productoDAO.getAll();
	}
	
	public List<Producto> getAllActivos() throws DataAccessException {
		return productoDAO.getAllActivos();
	}
	
	public List<Producto> getByClaveNombre(String buscarTexto) {
		return productoDAO.getByClaveNombre(buscarTexto);
	}

	public List<Producto> getByPrecio(String precio) {
		return productoDAO.getByPrecio(precio);
	}

	public Producto getByClave(String clave) {
		return productoDAO.getByClave(clave);
	}
	
	public void saveImportProducto(Producto producto){
		productoDAO.saveImportProducto(producto);
	}
	
	public String getNewCodeBar(){
		return productoDAO.getNewCodeBar();
	}
	
	public Producto getByCodigoBarras(String codigoBarras){
		return productoDAO.getByCodigoBarras(codigoBarras);
	}
	
	public void crearProcedimientos(){
		productoDAO.crearProcedimientos();
	}
}
