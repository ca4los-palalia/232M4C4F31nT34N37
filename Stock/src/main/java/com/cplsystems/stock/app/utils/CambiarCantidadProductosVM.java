package com.cplsystems.stock.app.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

import com.cplsystems.stock.domain.Producto;

@VariableResolver({ DelegatingVariableResolver.class })
public class CambiarCantidadProductosVM implements Serializable{
	private static final long serialVersionUID = 3098239433101641553L;
	@Wire("#cambiarCantidadProductosVMModalDialog")
	private Window cambiarCantidadProductosVMModalDialog;
	private String globalCommandName;
	
	private Producto producto;
	private String title;
	private String descripcion;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("itemFinder") String itemFinder,
			@ExecutionArgParam("productoSelected") Producto producto) {
		this.producto = producto;
		title = "Numero de piezas para " + producto.getNombre();
		
		Selectors.wireComponents(view, this, false);
		globalCommandName = itemFinder;
		
	}
	
	//-----------------------------------------------------------

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void transferProduct() {
		
		if (producto.getPrecioVenta() != null) {
			cambiarCantidadProductosVMModalDialog.detach();
			Map<String, Object> args = new HashMap();
			args.put("productoModificado", producto);
			if ((globalCommandName != null) && (!globalCommandName.isEmpty())) {
				BindUtils.postGlobalCommand(null, null, globalCommandName, args);
			} else {
				BindUtils.postGlobalCommand(null, null, "updateFromSelectedItem", args);
			}
		}else
			StockUtils.showSuccessmessage("Asigne un precio valido",
					Clients.NOTIFICATION_TYPE_WARNING, 0, null);
		
	}

	@Command
	public void closeDialog() {
		if (cambiarCantidadProductosVMModalDialog != null) {
			cambiarCantidadProductosVMModalDialog.detach();
		}
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
    
}
