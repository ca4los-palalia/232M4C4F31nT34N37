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
public class ConfirmDeleteProductoVM extends BasicStructure {
	private static final long serialVersionUID = 3098239433101641553L;
	@Wire("#confirmDeleteProductoModalDialog")
	private Window confirmDeleteProductoModalDialog;
	private String globalCommandName;
	
	
	private Producto producto;
	private String winTitle;
	private String winContent;
	private int indice;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("executeMethod") String executeMethod,
			@ExecutionArgParam("producto") Producto producto,
			@ExecutionArgParam("indice") int indice) {
		Selectors.wireComponents(view, this, false);
		globalCommandName = executeMethod;
		this.producto = producto;
		this.indice = indice;
		
		winTitle = "Eliminar el producto " + producto.getNombre();
		winContent = "¿Realmente desea eliminar el producto " + producto.getNombre() + "?\n\n"
				+ "*El producto se eliminara de forma permanente";
	}
	
	//-----------------------------------------------------------

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	
	@Command
	public void transferProduct() {
		confirmDeleteProductoModalDialog.detach();
		Map<String, Object> args = new HashMap();
		args.put("productoConfirm", producto);
		args.put("indice", indice);
		if ((globalCommandName != null) && (!globalCommandName.isEmpty()))
			BindUtils.postGlobalCommand(null, null, globalCommandName, args);
		else
			BindUtils.postGlobalCommand(null, null, "updateFromSelectedItem", args);
		
	
		
	}

	@Command
	public void closeDialog() {
		if (confirmDeleteProductoModalDialog != null) {
			confirmDeleteProductoModalDialog.detach();
		}
	}

	public String getWinTitle() {
		return winTitle;
	}
	
	public String getWinContent() {
		return winContent;
	}
	
	
}
