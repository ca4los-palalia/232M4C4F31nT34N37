package com.cplsystems.stock.app.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Window;

import com.cplsystems.stock.app.vm.BasicStructure;
import com.cplsystems.stock.domain.FileBean;
import com.cplsystems.stock.domain.Producto;

@VariableResolver({ DelegatingVariableResolver.class })
public class VisualizadorProductoVM extends BasicStructure {
	private static final long serialVersionUID = 3098239433101641553L;
	@Wire("#visualizadorProductoModalDialog")
	private Window visualizadorProductoModalDialog;
	private String globalCommandName;
	
	
	private Producto producto;
	private String winTitle;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("executeMethod") String executeMethod,
			@ExecutionArgParam("producto") Producto producto) {
		Selectors.wireComponents(view, this, false);
		globalCommandName = executeMethod;
		this.producto = producto;
		
		if(producto.getNombre() != null)
			winTitle = "Imagen del producto " + producto.getNombre();
		else
			winTitle = "No cuenta con imagen de producto";
	}
	
	//-----------------------------------------------------------

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	
	
	@Command
	public void closeDialog() {
		if (visualizadorProductoModalDialog != null) {
			visualizadorProductoModalDialog.detach();
		}
	}

	public String getWinTitle() {
		return winTitle;
	}

	public Producto getProducto() {
		return producto;
	}
	
	
}
