package com.cplsystems.stock.dao.impl;

import com.cplsystems.stock.app.utils.HibernateDAOSuportUtil;
import com.cplsystems.stock.app.utils.SessionUtils;
import com.cplsystems.stock.dao.LugarDAO;
import com.cplsystems.stock.domain.Categoria;
import com.cplsystems.stock.domain.Lugar;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Producto;
import com.cplsystems.stock.domain.Proyecto;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LugarDAOImpl extends HibernateDAOSuportUtil implements LugarDAO {
	
	@Autowired
	private SessionUtils sessionUtils;

	private Organizacion getOrganizacion() {
		return (Organizacion) this.sessionUtils.getFromSession("FIRMA");
	}
	
	@Transactional
	public void save(Lugar lugar) {
		getHibernateTemplate().saveOrUpdate(lugar);
	}

	@Transactional
	public void update(Lugar lugar) {
		getHibernateTemplate().saveOrUpdate(lugar);
	}

	@Transactional
	public void delete(Lugar lugar) {
		getHibernateTemplate().delete(lugar);
	}

	@Transactional(readOnly = true)
	public Lugar getById(Long idLugar) {
		List<Lugar> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Lugar.class);
		criteria.add(Restrictions.eq("idLugar", idLugar));
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos.get(0) : null;
	}

	@Transactional(readOnly = true)
	public Lugar getByIdProyecto(Proyecto proyecto) {
		return null;
	}

	@Transactional(readOnly = true)
	public Lugar getByNombre(String nombre) {
		List<Lugar> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Lugar.class);
		criteria.add(Restrictions.eq("nombre", nombre));
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos.get(0) : null;
	}

	@Transactional(readOnly = true)
	public List<Lugar> getAll() {
		List<Lugar> elementos = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Lugar.class);
		elementos = criteria.list();
		return elementos.size() > 0 ? elementos : null;
	}
}
