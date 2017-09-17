package com.cplsystems.stock.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;




import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cplsystems.stock.app.utils.HibernateDAOSuportUtil;
import com.cplsystems.stock.app.utils.SessionUtils;
import com.cplsystems.stock.app.utils.StockUtils;
import com.cplsystems.stock.dao.ProductoDAO;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Producto;


@Repository
public class ProductoDAOImpl extends HibernateDAOSuportUtil implements ProductoDAO {

	private String mensajeReturn = "";
	
	@Autowired
	private SessionUtils sessionUtils;

	private Organizacion getOrganizacion() {
		return (Organizacion) this.sessionUtils.getFromSession("FIRMA");
	}

	@Transactional
	public void save(Producto producto) {
		getHibernateTemplate().saveOrUpdate(producto);
	}

	@Transactional
	public void delete(Producto producto) {
		getHibernateTemplate().delete(producto);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Producto getById(Long idProducto) {
		List<Producto> producto = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.setFetchMode("unidad", FetchMode.JOIN);
		criteria.add(Restrictions.eq("idProducto", idProducto));
		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		producto = criteria.list();
		return producto.size() > 0 ? (Producto) producto.get(0) : null;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Producto> getAll() {
		List<Producto> producto = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		producto = criteria.list();
		
		return producto.size() > 0 ? producto : null;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Producto> getByClaveNombre(String buscarTexto) {
		List<Producto> lista = null;

		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.add(Restrictions.ilike("clave", "%" + buscarTexto + "%"));
		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		criteria.addOrder(Order.asc("idProducto"));

		lista = criteria.list();
		if ((lista.equals(null)) || (lista.size() < 1)) {
			Criteria criteria2 = getHibernateTemplate().getSessionFactory().openSession()
					.createCriteria(Producto.class);

			criteria2.add(Restrictions.ilike("nombre", "%" + buscarTexto + "%"));
			criteria2.add(Restrictions.eq("organizacion", getOrganizacion()));
			criteria2.addOrder(Order.asc("idProducto"));
			lista = criteria2.list();
		}
		return (lista != null) && (!lista.isEmpty()) ? lista : null;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Producto> getByPrecio(String precio) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.addOrder(Order.asc("nombre"));
		criteria.add(Restrictions.sqlRestriction(" precio LIKE '" + precio + "%'"));

		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		List<Producto> tipo = criteria.list();
		return tipo.size() > 0 ? tipo : null;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Producto getByClave(String clave) {
		List<Producto> lista = null;

		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.add(Restrictions.eq("clave", clave));
		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		criteria.setMaxResults(1);

		lista = criteria.list();
		return (lista != null) && (!lista.isEmpty()) ? (Producto) lista.get(0) : null;
	}
	
	@Transactional(readOnly = false)
	public void saveImportProducto(final Producto producto){
		
		getSession().doWork(new Work() {
            @Override
            public void execute(Connection cnctn) throws SQLException {
                
            	
            	
            	CallableStatement callableStatement = null;
                String query = "{call stock.insert_producto_import(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

                try {
                    callableStatement = cnctn.prepareCall(query);
                    Integer existencia = producto.getEnExistencia();
                    if(producto.getEnExistencia() == null)
                        existencia = 0;
                    Double compra = producto.getPrecioCompra();
                    if(compra == null)
                        compra = 0D;
                    Double venta = producto.getPrecioVenta();
                    if(venta == null)
                        venta = 0D;
                    Integer minimo = producto.getMinimo();
                    if(minimo == null)
                        minimo = 0;
                    new StockUtils().convertirCalendarToString(producto.getFechaActualizacion());
                    java.sql.Date javaSqlDate = new java.sql.Date(producto.getFechaActualizacion().getTime().getTime());
                    
                    callableStatement.setString(1, producto.getNombre());
                    callableStatement.setInt(2, existencia);
                    callableStatement.setDouble(3, compra);
                    callableStatement.setDouble(4, venta);
                    callableStatement.setString(5, producto.getParamMarca());
                    callableStatement.setString(6, producto.getParamLugar());
                    callableStatement.setString(7, producto.getParamUnidad());
                    callableStatement.setString(8, producto.getParamCategoria());
                    callableStatement.setString(9, producto.getCodigoBarras());
                    callableStatement.setInt(10, minimo);
                    callableStatement.setString(11, producto.getDescripcion());
                    callableStatement.setString(12, producto.getParamNaturaleza());
                    callableStatement.setLong(13, producto.getOrganizacion().getIdOrganizacion());
                    callableStatement.setLong(14, producto.getUsuario().getIdUsuario());
                    callableStatement.setDate(15, javaSqlDate);
                    //callableStatement.registerOutParameter(15, java.sql.Types.BIGINT);
                    callableStatement.executeUpdate();
                    //callableStatement.getInt(16);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            	
            	//ejecutarInsercion(cnctn, producto);
            }
        });
	}
	
	
	@Transactional(readOnly = false)
	public String getNewCodeBar(){
		getSession().doWork(new Work() {
            @Override
            public void execute(Connection cnctn) throws SQLException {
            	CallableStatement callableStatement = null;
                String query = "{call get_new_code_bar(?)}";
                callableStatement = cnctn.prepareCall(query);

                callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
                callableStatement.executeUpdate();
                mensajeReturn = callableStatement.getString(1);
            }
        });
		return mensajeReturn;
	}

	@Transactional(readOnly = true)
	public List<Producto> getAllActivos() {
		List<Producto> producto = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		criteria.add(Restrictions.eq("activo", true));
		producto = criteria.list();
		return producto.size() > 0 ? producto : null;
	}

	@Transactional(readOnly = true)
	public Producto getByCodigoBarras(String codigoBarras) {
		List<Producto> producto = null;
		Criteria criteria = getHibernateTemplate().getSessionFactory().openSession().createCriteria(Producto.class);

		criteria.add(Restrictions.eq("organizacion", getOrganizacion()));
		criteria.add(Restrictions.eq("codigoBarras", codigoBarras));
		producto = criteria.list();
		return producto.size() > 0 ? producto.get(0) : null;
	}

	private String statusSpCreated;
	@Transactional(readOnly = false)
	public void crearProcedimientos() {
		try {
			statusSpCreated = "";
			File file = new File(getClass().getClassLoader().getResource("storeProcedures").toURI());
			walkin(file);
			System.err.println(statusSpCreated);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void walkin(File dir) {
        File listFile[] = dir.listFiles();
        
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory())
                    walkin(listFile[i]);
                else{
                	String temp = listFile[i].getName();
                	String extension = temp.substring(temp.indexOf(".") + 1);
                	try {
                		if(extension.contains("sql")){
                			ejecutarSql(listFile[i]);
                    		statusSpCreated += "SP: " + listFile[i].getName() + " [Creado]\n";
                		}
                    		
					} catch (Exception e) {
						statusSpCreated +="SP: " + listFile[i].getName() + " ya existe [nada por hacer]\n";
					}
                	
                }
                	
            }
        }
    }
	
	private String getQuery(File file) {
		String query = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String strLine;
			
			while ((strLine = br.readLine()) != null)   {
				String replace = strLine.replaceAll("(\r\n|\n|\r)", "\\t");
				query += " " + replace;
			}
			
		} catch (Exception e) {
			System.out.println("El fichero " + file.getAbsolutePath() + " no se encuentra en el sistema");
		}
		return query;
	}
	
	
	private void ejecutarSql(final File file){
		final String queryFile = getQuery(file);		
		
		getSession().doWork(new Work() {
            @Override
            public void execute(Connection cnctn) throws SQLException {
            	Statement stmt = cnctn.createStatement();
            	stmt.execute(queryFile);
            }
        });
		
	}
}
