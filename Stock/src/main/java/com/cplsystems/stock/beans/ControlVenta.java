package com.cplsystems.stock.beans;

import java.io.Serializable;

public class ControlVenta implements Serializable{

	private static final long serialVersionUID = -239023166619311507L;
	private String subtotalTag;
	private String descuentoTag;
	private String totalTag;
	
	private Float subtotal;
	private Float descuento;
	private Float total;
	public String getSubtotalTag() {
		if(subtotal != null)
			subtotalTag = "$" + subtotal.toString();
		return subtotalTag;
	}
	public void setSubtotalTag(String subtotalTag) {
		this.subtotalTag = subtotalTag;
	}
	public String getDescuentoTag() {
		if(descuento != null)
			descuentoTag = "$" + descuento.toString();
		return descuentoTag;
	}
	public void setDescuentoTag(String descuentoTag) {
		this.descuentoTag = descuentoTag;
	}
	public String getTotalTag() {
		if(total != null)
			totalTag = "$" + total.toString();
		return totalTag;
	}
	public void setTotalTag(String totalTag) {
		this.totalTag = totalTag;
	}
	public Float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}
	public Float getDescuento() {
		return descuento;
	}
	public void setDescuento(Float descuento) {
		this.descuento = descuento;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	

	
	
}
