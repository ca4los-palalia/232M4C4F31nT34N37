package com.cplsystems.stock.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.cplsystems.stock.app.vm.BasicStructure;
import com.cplsystems.stock.domain.ConfiguraImpresion;
import com.cplsystems.stock.domain.Producto;

@VariableResolver({ DelegatingVariableResolver.class })
public class ConfiguracionImpresionCodigosVM extends BasicStructure {
	private static final long serialVersionUID = 3098239433101641553L;
	@Wire("#configuracionImpresionCodigosModalDialog")
	private Window configuracionImpresionCodigosModalDialog;
	private String globalCommandName;
	
	private List<Producto> renglones;
	private Producto producto;
	private String winTitle;
	
	private Integer contadorHojas;
	private Integer contadorRenglones;
	
	private ConfiguraImpresion configuraImpresion;
	private List<ConfiguraImpresion> configuraImpresiones;
	/*
	private boolean configuracionPorHojas;
	private boolean configuracionPorrenglones;
	private boolean deshabilitarSpinnerHojas;
	private boolean deshabilitarSpinnerRenglones;
	*/
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("executeMethod") String executeMethod,
			@ExecutionArgParam("producto") Producto producto) {
		Selectors.wireComponents(view, this, false);
		globalCommandName = executeMethod;
		this.producto = producto;
		renglones = new ArrayList<Producto>();
		
		if(producto.getNombre() != null)
			winTitle = "Imagen del producto " + producto.getNombre();
		else
			winTitle = "No cuenta con imagen de producto";
		
		contadorHojas = 0;
		contadorRenglones = 0;
		/*
		deshabilitarSpinnerHojas = true;
		deshabilitarSpinnerRenglones = true;
		*/
		
		configuraImpresion = new ConfiguraImpresion();
		configuraImpresiones = new ArrayList<ConfiguraImpresion>();
		
		ConfiguraImpresion op1 = new ConfiguraImpresion();
		op1.setNombre("Por Hojas");
		op1.setDeshabilitarSpinner(true);
		
		ConfiguraImpresion op2 = new ConfiguraImpresion();
		op2.setNombre("Por Renglones");
		op2.setDeshabilitarSpinner(true);
		configuraImpresiones.add(op1);
		configuraImpresiones.add(op2);
	}
	
	@Command
	@NotifyChange({ "renglones", "contadorRenglones" })
	public void addRow(){
		System.err.println(contadorRenglones);
		renglones = new ArrayList<Producto>();
		for (int i = 0; i < contadorRenglones; i++) {
			renglones.add(producto);
		}
		
	}
	
	@Command
	@NotifyChange({ "renglones" })
	public void removeRow(){
		if(renglones.size() > 0)
			renglones.remove(renglones.size()-1);
	}
	
	@Command
	@NotifyChange({ "deshabilitarSpinnerHojas", "deshabilitarSpinnerRenglones" })
	public void radioButtonSelected(@ContextParam(ContextType.VIEW) Component view){
		configuraImpresion.getNombre();
			
		
		System.err.println();
	}
	//-----------------------------------------------------------

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	
	
	@Command
	public void closeDialog() {
		if (configuracionImpresionCodigosModalDialog != null) {
			configuracionImpresionCodigosModalDialog.detach();
		}
	}

	public String getWinTitle() {
		return winTitle;
	}

	public Producto getProducto() {
		return producto;
	}

	public List<Producto> getRenglones() {
		return renglones;
	}

	public void setRenglones(List<Producto> renglones) {
		this.renglones = renglones;
	}

	public Integer getContadorHojas() {
		return contadorHojas;
	}

	public void setContadorHojas(Integer contadorHojas) {
		this.contadorHojas = contadorHojas;
	}

	public Integer getContadorRenglones() {
		return contadorRenglones;
	}

	public void setContadorRenglones(Integer contadorRenglones) {
		this.contadorRenglones = contadorRenglones;
	}

	public ConfiguraImpresion getConfiguraImpresion() {
		return configuraImpresion;
	}

	public void setConfiguraImpresion(ConfiguraImpresion configuraImpresion) {
		this.configuraImpresion = configuraImpresion;
	}

	public List<ConfiguraImpresion> getConfiguraImpresiones() {
		return configuraImpresiones;
	}

	public void setConfiguraImpresiones(
			List<ConfiguraImpresion> configuraImpresiones) {
		this.configuraImpresiones = configuraImpresiones;
	}
	
}
