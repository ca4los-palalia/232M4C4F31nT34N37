package com.cplsystems.stock.dao.impl;

import com.cplsystems.stock.app.utils.HibernateDAOSuportUtil;
import com.cplsystems.stock.app.utils.SessionUtils;
import com.cplsystems.stock.dao.MarcaDAO;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Marca;
import com.cplsystems.stock.domain.Usuarios;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MarcaDAOImpl extends HibernateDAOSuportUtil implements MarcaDAO {
	@Autowired
	private SessionUtils sessionUtils;

	private Organizacion getOrganizacion() {
		return (Organizacion) this.sessionUtils.getFromSession("FIRMA");
	}

	@Transactional
	public void save(Marca marca) {
		getHibernateTemplate().saveOrUpdate(marca);
	}

	@Transactional
	public void update(Marca marca) {
		getHibernateTemplate().update(marca);
	}

	@Transactional
	public void delete(Marca marca) {
		getHibernateTemplate().delete(marca);
	}

	@Transactional(readOnly = true)
	public Marca getById(Long idMarca) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Marca.class);

		criteria.add(Restrictions.eq("idMarca", idMarca));
		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		List<Marca> lista = criteria.list();
		return (lista != null) && (!lista.isEmpty()) ? (Marca) lista.get(0) : null;
	}

	@Transactional(readOnly = true)
	public List<Marca> getAll() {
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Marca.class);
		List<Marca> lista = criteria.list();
		return (lista != null) && (!lista.isEmpty()) ? lista : null;
	}

	@Transactional(readOnly = true)
	public Marca getByNombre(String nombre) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Marca.class);

		criteria.add(Restrictions.eq("nombre", nombre));
		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		List<Marca> lista = criteria.list();
		return (lista != null) && (!lista.isEmpty()) ? (Marca) lista.get(0) : null;
	}
}
