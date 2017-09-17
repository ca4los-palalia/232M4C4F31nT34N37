package com.cplsystems.stock.dao;

import com.cplsystems.stock.domain.Categoria;
import com.cplsystems.stock.domain.Lugar;
import com.cplsystems.stock.domain.Proyecto;

import java.util.List;

import org.springframework.dao.DataAccessException;

public abstract interface CategoriaDAO {

	public void save(Categoria categoria);

	public void delete(Categoria categoria);

	public Categoria getById(Long idCategoria);

	public Categoria getByNombre(String nombre);

	public List<Categoria> getAll();
}
