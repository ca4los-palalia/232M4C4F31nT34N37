package com.cplsystems.stock.dao;

import java.util.List;

import com.cplsystems.stock.domain.Marca;

public abstract interface MarcaDAO {
	public abstract void save(Marca marca);

	public abstract void update(Marca marca);

	public abstract void delete(Marca marca);

	public abstract Marca getById(Long idMarca);

	public abstract List<Marca> getAll();

	public abstract Marca getByNombre(String nombre);
}
