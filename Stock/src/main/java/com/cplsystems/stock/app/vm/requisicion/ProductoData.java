package com.cplsystems.stock.app.vm.requisicion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cplsystems.stock.domain.Producto;
import com.cplsystems.stock.domain.ProductoFiltro;

public class ProductoData {
	static List<Producto> globalProductos;
	public ProductoData(List<Producto> globalProductos){
		this.globalProductos = globalProductos;
	}
	
	public static List<Producto> getFilterFoods(ProductoFiltro foodFilter) {
        List<Producto> somefoods = new ArrayList<Producto>();
        String findNombre = "*";
        String findCodigobarras = "*";
        
        if(foodFilter.getNombre() != null){
        	if(!foodFilter.getNombre().isEmpty())
        		findNombre = foodFilter.getNombre().toLowerCase();
        }
        
        if(foodFilter.getCodigoBarra() != null)
        	if(!foodFilter.getCodigoBarra().isEmpty())
        		findCodigobarras = foodFilter.getCodigoBarra().toLowerCase();
        
        //String nut = foodFilter.getCodigoBarra().toLowerCase();
        
        if(!findNombre.equals("*") || !findCodigobarras.equals("*")){
        	for (Iterator<Producto> i = globalProductos.iterator(); i.hasNext();) {
            	Producto tmp = i.next();
            	
            	String matchNombre = "*";
            	String matchCodigobarras = "*";
            	
            	if(tmp.getNombre() != null)
            		matchNombre = tmp.getNombre();
            	if(tmp.getCodigoBarras() != null)
            		matchCodigobarras = tmp.getCodigoBarras();
            	
            	
            	if(!findNombre.equals("*") && findCodigobarras.equals("*")){
            		if (matchNombre.toLowerCase().contains(findNombre.toLowerCase())) {
                        somefoods.add(tmp);
                    }
            	}else if(findNombre.equals("*") && !findCodigobarras.equals("*")){
            		if (matchCodigobarras.toLowerCase().contains(findCodigobarras)) {
                        somefoods.add(tmp);
                    }
            	}else if(!findNombre.equals("*") && !findCodigobarras.equals("*")){//FILTRAR LAS DOS COLUMNAS
            		if (matchNombre.toLowerCase().contains(findNombre.toLowerCase()) && 
            				matchCodigobarras.toLowerCase().contains(findCodigobarras)) {
                        somefoods.add(tmp);
                    }
            	}
        		
            	
            }
        }
        
        if(findNombre.equals("*") && findCodigobarras.equals("*"))
        	return globalProductos;
        else
        	return somefoods;
    }
}

	
	
