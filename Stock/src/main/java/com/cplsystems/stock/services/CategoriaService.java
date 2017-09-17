package com.cplsystems.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cplsystems.stock.dao.CategoriaDAO;
import com.cplsystems.stock.domain.Categoria;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaDAO categoriaDAO;

	public void save(Categoria categoria) throws DataAccessException {
		this.categoriaDAO.save(categoria);
	}

	public void delete(Categoria categoria) throws DataAccessException {
		this.categoriaDAO.delete(categoria);
	}

	public Categoria getById(Long idCategoria) throws DataAccessException {
		return this.categoriaDAO.getById(idCategoria);
	}

	
	public Categoria getByNombre(String nombre) throws DataAccessException {
		return this.categoriaDAO.getByNombre(nombre);
	}

	public List<Categoria> getAll() throws DataAccessException {
		return this.categoriaDAO.getAll();
	}
}
