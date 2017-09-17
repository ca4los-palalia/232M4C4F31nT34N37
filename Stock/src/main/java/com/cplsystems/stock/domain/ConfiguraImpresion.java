package com.cplsystems.stock.domain;

public class ConfiguraImpresion {

	private boolean checked;
	private String nombre;
	private boolean deshabilitarSpinner;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		if(checked)
			deshabilitarSpinner = false;
		else
			deshabilitarSpinner = true;
			
		this.checked = checked;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isDeshabilitarSpinner() {
		return deshabilitarSpinner;
	}

	public void setDeshabilitarSpinner(boolean deshabilitarSpinner) {
		this.deshabilitarSpinner = deshabilitarSpinner;
	}

}
