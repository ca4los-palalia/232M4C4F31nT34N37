package com.cplsystems.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cplsystems.stock.dao.MarcaDAO;
import com.cplsystems.stock.domain.Marca;

@Service
public class MarcaService {
	@Autowired
	private MarcaDAO marcaDAO;

	public void save(Marca marca) throws DataAccessException {
		this.marcaDAO.save(marca);
	}

	public void update(Marca marca) throws DataAccessException {
		this.marcaDAO.update(marca);
	}

	public void delete(Marca marca) throws DataAccessException {
		this.marcaDAO.delete(marca);
	}

	public Marca getById(Long idMarca) throws DataAccessException {
		return this.marcaDAO.getById(idMarca);
	}

	public List<Marca> getAll() throws DataAccessException {
		return this.marcaDAO.getAll();
	}

	public Marca getByNombre(String nombre) throws DataAccessException {
		return this.marcaDAO.getByNombre(nombre);
	}
}
