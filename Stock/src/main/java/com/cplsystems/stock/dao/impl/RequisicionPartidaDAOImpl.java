package com.cplsystems.stock.dao.impl;

import com.cplsystems.stock.app.utils.HibernateDAOSuportUtil;
import com.cplsystems.stock.dao.RequisicionPartidaDAO;
import com.cplsystems.stock.domain.Partida;
import com.cplsystems.stock.domain.Requisicion;
import com.cplsystems.stock.domain.RequisicionPartida;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RequisicionPartidaDAOImpl extends HibernateDAOSuportUtil implements RequisicionPartidaDAO {
	public void save(RequisicionPartida requisicionPartida) {
	}

	public void update(RequisicionPartida requisicionPartida) {
	}

	public void delete(RequisicionPartida requisicionPartida) {
	}

	public RequisicionPartida getById(Long idRequisicionPartida) {
		return null;
	}

	public List<RequisicionPartida> getByPartida(Partida partida) {
		return null;
	}

	public List<RequisicionPartida> getByRequisicion(Requisicion requisicion) {
		return null;
	}

	public List<RequisicionPartida> getAll() {
		return null;
	}
}
