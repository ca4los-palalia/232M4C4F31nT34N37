package com.cplsystems.stock.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cplsystems.stock.app.utils.HibernateDAOSuportUtil;
import com.cplsystems.stock.app.utils.SessionUtils;
import com.cplsystems.stock.dao.CategoriaDAO;
import com.cplsystems.stock.domain.Categoria;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Producto;


@Repository
public class CategoriaDAOImpl extends HibernateDAOSuportUtil implements CategoriaDAO {

	@Autowired
	private SessionUtils sessionUtils;

	private Organizacion getOrganizacion() {
		return (Organizacion) this.sessionUtils.getFromSession("FIRMA");
	}
	
	@Transactional
	public void save(Categoria categoria) {
		getHibernateTemplate().saveOrUpdate(categoria);
	}

	@Transactional
	public void delete(Categoria categoria) {
		getHibernateTemplate().delete(categoria);
	}

	@Transactional(readOnly = true)
	public Categoria getById(Long idCategoria) {
		List<Categoria> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Categoria.class);

		//criteria.setFetchMode("unidad", FetchMode.JOIN);
		criteria.add(Restrictions.eq("idCategoria", idCategoria));
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos.get(0) : null;
	}

	@Transactional(readOnly = true)
	public Categoria getByNombre(String nombre) {
		List<Categoria> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Categoria.class);
		criteria.add(Restrictions.eq("nombre", nombre));
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos.get(0) : null;
	}

	@Transactional(readOnly = true)
	public List<Categoria> getAll() {
		List<Categoria> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Categoria.class);
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos : null;
	}
	
}
