package com.cplsystems.stock.app.vm.requisicion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

import com.cplsystems.stock.app.vm.requisicion.utils.RequisicionVariables;
import com.cplsystems.stock.beans.ControlVenta;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Producto;
import com.cplsystems.stock.domain.Usuarios;

@VariableResolver({ DelegatingVariableResolver.class })
public class concentradoVM extends RequisicionVariables {
	private static final long serialVersionUID = 2584088569805199520L;
	public static final String REQUISICION_GLOBALCOMMAND_NAME_FOR_UPDATE = "updateCommandFromItemFinder";

	private List<Producto> venderProductos;
	private List<Producto> catalogoProductos;
	private Producto productoCatalogo;
	private ControlVenta controlVenta;
	private String titleProductosCatalogo;
	private String inputSearching;
	
	@Init
	public void init() {
		super.init();
		inicialiarCOntrolVenta();
		catalogoProductos = productoService.getAllActivos();
		venderProductos = new ArrayList<Producto>();
		titleProductosCatalogo = "Catalogo de productos (" + catalogoProductos.size() + ")";
		usuario = (Usuarios) sessionUtils.getFromSession("usuario");
		organizacion = (Organizacion) sessionUtils.getFromSession("FIRMA");
	}
	private void inicialiarCOntrolVenta(){
		controlVenta = new ControlVenta();
		controlVenta.setDescuento(0F);
		controlVenta.setSubtotal(0F);
		controlVenta.setTotal(0F);
	}
	
	@Command
	@NotifyChange({"catalogoProductos", "titleProductosCatalogo"})
	//public void findByNombreCodeBar(@BindingParam("v") String value, @ContextParam(ContextType.TRIGGER_EVENT) InputEvent event){
	public void findByNombreCodeBar(@BindingParam("text") String searchText){
		/*
		inputSearching = null;
		if(!event.getValue().equals(""))
			inputSearching = event.getValue();
		*/
			
		if(inputSearching == null || inputSearching.equals("*")){
			catalogoProductos = productoService.getAllActivos();
			titleProductosCatalogo = "Catalogo de productos (" + catalogoProductos.size() + ")";
		}else{
			productoCatalogo = productoService.getByCodigoBarras(inputSearching);
			if(productoCatalogo == null){
				catalogoProductos = productoService.getByClaveNombre(inputSearching);
				if(catalogoProductos != null)
					titleProductosCatalogo = "Catalogo de productos (" + catalogoProductos.size() + ")";
				else
					titleProductosCatalogo = "Catalogo de productos (0 resultados)";
			}else{
				titleProductosCatalogo = inputSearching + " Encontrado";
				asignarProductoToVenta();
			}
				
		}
		
	}
	
	@Command
	@NotifyChange("venderProductos")
	public void seleccionarProducto(){
		
		
	}
	
	@Command
	@NotifyChange({"venderProductos", "controlVenta"})
	public void agregarProductoProductoVenta(){
		if(productoCatalogo.getPrecioVenta() == null){
			Map<String, Object> inputParams = new HashMap();
			inputParams.put("itemFinder", "precioAsignadoProducto");

			Window productoModalView = stockUtils
					.createModelDialogWithParams("/modulos/productos/utils/agregarPrecioProducto.zul", inputParams);
			productoModalView.doModal();
		}else{
			asignarProductoToVenta();
		}
		
		
		
	}
	
	@Command
	@NotifyChange({"venderProductos", "controlVenta"})
	public void precioAsignadoProducto(@BindingParam("productoModificado") Producto productoModificado){
		if(productoModificado.getPrecioVenta() != null){
			productoCatalogo.setPrecioVenta(productoModificado.getPrecioVenta());
			asignarProductoToVenta();
			
		}
	}
	
	@Command
	@NotifyChange("venderProductos")
	public void realizarBusqueda(){
		
		
	}
	
	@Command
	@NotifyChange({"venderProductos" , "controlVenta", "producto", "productoCatalogo"})
	public void eliminarProductoVenta(@BindingParam("index") Integer index){
		producto = new Producto();
		productoCatalogo = new Producto();
		Producto p = venderProductos.get(index);
		venderProductos.remove(p);
		actualizarCuenta();
	}
	
	//******************************
	
	
	private void asignarProductoToVenta(){
		boolean update = actualizaCantidadDeProductoExistenteAVender();
		productoCatalogo.setVentaSubtotal(new Float(productoCatalogo.getVentaCantidad() * productoCatalogo.getPrecioVenta()));
		
		if(!update)
			venderProductos.add(productoCatalogo);
		
		actualizarCuenta();
		/*
		controlVenta.setSubtotal(productoCatalogo.getVentaSubtotal() + controlVenta.getSubtotal());
		controlVenta.setTotal(controlVenta.getTotal() + controlVenta.getSubtotal());
		*/
	}
	private void actualizarCuenta() {
		inicialiarCOntrolVenta();
		float sumaTotal = 0;
		for (Producto item : venderProductos) {
			sumaTotal += item.getVentaSubtotal();
		}
		
		if(venderProductos.size() > 0)
			controlVenta.setSubtotal(sumaTotal);	
		else{
			if(productoCatalogo.getIdProducto() != null)
				controlVenta.setSubtotal(new Float(productoCatalogo.getPrecioVenta()));
			else
				controlVenta.setSubtotal(0F);
		}
			
		controlVenta.setTotal(controlVenta.getSubtotal() - controlVenta.getDescuento());
		
	}



	private boolean actualizaCantidadDeProductoExistenteAVender(){
		boolean update = false;
		for (Producto item : venderProductos) {
			if(item.getIdProducto().equals(productoCatalogo.getIdProducto())){
				item.setVentaCantidad(item.getVentaCantidad() + 1);
				item.setVentaSubtotal(new Float(item.getVentaCantidad() * item.getPrecioVenta()));
				update = true;
				break;
			}
		}
		
		if(!update)
			productoCatalogo.setVentaCantidad(1);
		return update;
	}
	
	public List<Producto> getVenderProductos() {
		return venderProductos;
	}

	public void setVenderProductos(List<Producto> venderProductos) {
		
		this.venderProductos = venderProductos;
	}



	public ControlVenta getControlVenta() {
		return controlVenta;
	}



	public void setControlVenta(ControlVenta controlVenta) {
		this.controlVenta = controlVenta;
	}



	public List<Producto> getCatalogoProductos() {
		return catalogoProductos;
	}



	public void setCatalogoProductos(List<Producto> catalogoProductos) {
		this.catalogoProductos = catalogoProductos;
	}



	public Producto getProductoCatalogo() {
		return productoCatalogo;
	}



	public void setProductoCatalogo(Producto productoCatalogo) {
		this.productoCatalogo = productoCatalogo;
	}
	public String getTitleProductosCatalogo() {
		return titleProductosCatalogo;
	}
	public void setTitleProductosCatalogo(String titleProductosCatalogo) {
		this.titleProductosCatalogo = titleProductosCatalogo;
	}
	public String getInputSearching() {
		return inputSearching;
	}
	public void setInputSearching(String inputSearching) {
		this.inputSearching = inputSearching;
	}
	
	
	

	

}
