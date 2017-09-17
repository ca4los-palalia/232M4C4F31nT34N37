package com.cplsystems.stock.app.vm.requisicion;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cplsystems.stock.app.utils.StockUtils;
import com.cplsystems.stock.domain.Marca;
import com.cplsystems.stock.domain.Organizacion;
import com.cplsystems.stock.domain.Producto;
import com.cplsystems.stock.domain.ProductoFiltro;
import com.cplsystems.stock.domain.Usuarios;

@VariableResolver({ DelegatingVariableResolver.class })
public class RequisicionVM extends RequisicionMetaClass {
	private static final long serialVersionUID = 2584088569805199520L;
	// public static final String REQUISICION_GLOBALCOMMAND_NAME_FOR_UPDATE =
	// "updateCommandFromItemFinder";
	
	private ProductoFiltro productoFilter = new ProductoFiltro();
	private ProductoData listaGlobal;

	@Init
	public void init() {
		super.init();
		
		producto = new Producto();
		producto.setActivo(false);
		producto.setMarca(new Marca());
		productosDB = productoService.getAll();
		if(productosDB == null)
			productosDB = new ArrayList<Producto>();
		listaGlobal = new ProductoData(productosDB);

		unidadesDB = unidadService.getAll();
		marcas = marcaService.getAll();

		productosNaturalezas = productoNaturalezaService.getAll();
		// productoNaturaleza

		lugares = lugarService.getAll();
		categorias = categoriaService.getAll();
		
		usuario = (Usuarios) sessionUtils.getFromSession("usuario");
		organizacion = (Organizacion) sessionUtils.getFromSession("FIRMA");

	}


	public List<Producto> getProductosFiltrados(Producto productoFinder) {

		List<Producto> filteringList = new ArrayList<Producto>();
		String palabraDeBusqueda = null;

		if (productoFinder != null && productoFinder.getDescripcion() != null)
			palabraDeBusqueda = productoFinder.getDescripcion().toLowerCase();

		for (Iterator<Producto> i = productosDB.iterator(); i.hasNext();) {
			Producto tmp = i.next();
			if (tmp.getNombre().toLowerCase().contains(palabraDeBusqueda)
					&& !palabraDeBusqueda.trim().isEmpty()) {
				filteringList.add(tmp);
			}
		}

		if (filteringList.size() > 0)
			return filteringList;
		else
			return productosDB;

	}

	@Command
	@NotifyChange({ "producto" })
	public void generarCodigoBarras() {
		if (producto.getCodigoBarras() != null
				&& producto.getCodigoBarras().matches("[0-9]*")) {
			Messagebox.show(
					"¿Deseas actualizar el codigo de barras del producto "
							+ producto.getNombre(), "Question", 3,
					"z-msgbox z-msgbox-question", new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == 1) {
								producto.setCodigoBarras(productoService
										.getNewCodeBar());
							}
						}
					});
		} else
			producto.setCodigoBarras(productoService.getNewCodeBar());

	}

	@Command
	@NotifyChange({ "producto" })
	public void seleccionarProducto() {
		if (producto.getUnidad() != null) {
			producto.setUnidad(getUnidadFromList(producto.getUnidad()
					.getIdUnidad()));

		}
		if (producto.getProductoNaturaleza() != null) {
			producto.setProductoNaturaleza(getProductoNaturalezaFromList(producto
					.getProductoNaturaleza().getIdProductoNaturaleza()));

		}
		if (producto.getLugar() != null) {
			producto.setLugar(getLugaresFromList(producto.getLugar()
					.getIdLugar()));

		}

		if (producto.getCategoria() != null) {
			producto.setCategoria(getCategoriaFromList(producto.getCategoria()
					.getIdCategoria()));
		}
		if (producto.getActivo() == null)
			producto.setActivo(false);
	}

	@Command
	@NotifyChange({ "producto" })
	public void nuevoProducto() {
		producto = new Producto();
		producto.setActivo(false);
	}

	@Command
	@NotifyChange({ "productoModel", "footer", "producto" })
	public void guardarProducto() {
		String mensaje = "";
		if (producto != null) {
			if (producto.getNombre() != null && !producto.getNombre().isEmpty()) {
				producto.setActivo(true);
				producto.setOrganizacion(organizacion);
				producto.setUsuario(usuario);
				producto.setFechaActualizacion(Calendar.getInstance());
				productoService.save(producto);
				if(!productosDB.contains(producto)){
					mensaje = "El producto " + producto.getNombre()
							+ " ha sido actualizado";
					productosDB.add(producto);
					listaGlobal = new ProductoData(productosDB);
				}else
					mensaje = "El producto " + producto.getNombre()
					+ " ha sido Creado";
					
				StockUtils.showSuccessmessage(
						mensaje, "info", 0, null);

			} else
				StockUtils.showSuccessmessage(
						"Nombre del producto es requerido", "warning", 0, null);
		} else
			StockUtils
					.showSuccessmessage(
							"Clic en el boton nuevo y llenar la informacion para guardar un nuevo producto",
							"warning", 0, null);
	}

	@Command
	public void eliminarProductoQuestion(@BindingParam("index") Integer index) {
		Producto p = productosDB.get(index);
		
		Map<String, Object> inputParams = new HashMap();
		inputParams.put("executeMethod", "eliminarProducto");
		inputParams.put("producto", p);
		inputParams.put("indice", index);

		Window productoModalView = stockUtils.createModelDialogWithParams(
				"/modulos/productos/utils/confirmDeleteProducto.zul",
				inputParams);
		productoModalView.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "productoModel", "footer", "producto" })
	public void eliminarProducto(
			@BindingParam("productoConfirm") Producto productoConfirm,
			@BindingParam("indice") int indice) {
		
		productosDB.remove(indice);
		listaGlobal = new ProductoData(productosDB);
		
		productoService.delete(productoConfirm);

		StockUtils.showSuccessmessage(
				"el producto -" + productoConfirm.getNombre()
						+ "- ha sido eliminado de forma permanente", "info", Integer.valueOf(0),
				null);

		producto = new Producto();
		producto.setActivo(false);
	}

	@Command
	@NotifyChange({ "productoModel", "footer", "producto" })
	public void onUploadExcel(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
			throws IOException {

		productosDB = leerDatosDesdeExcel(getStreamMediaExcel(ctx));
		
		if (productosDB.size() > 0) {
			for (Producto item : productosDB) {
				productoService.save(item);
			}
			
			listaGlobal = new ProductoData(productosDB);
			nuevoProducto(); 
			Messagebox.show(productosDB.size() + " Productos Importados");
		} else
			Messagebox
					.show("No se importaron Productos. El documento esta vacio");
	}

	@SuppressWarnings("rawtypes")
	private List<Producto> leerDatosDesdeExcel(InputStream inPutStream) {
		List<Producto> productoNuevosExcel = new ArrayList<Producto>();
		Usuarios usuario = (Usuarios) sessionUtils.getFromSession("usuario");
		Organizacion organizacion = (Organizacion) sessionUtils
				.getFromSession("FIRMA");
		try {
			XSSFWorkbook workBook = new XSSFWorkbook(inPutStream);
			XSSFSheet hssfSheet = workBook.getSheetAt(0);
			Iterator rowIterator = hssfSheet.rowIterator();
			Integer i = 0;
			while (rowIterator.hasNext()) {
				Producto producto = new Producto();
				XSSFRow hssfRow = (XSSFRow) rowIterator.next();
				Iterator iterator = hssfRow.cellIterator();
				if (i > 0) {
					int j = 0;
					while (iterator.hasNext()) {
						if (j < 14) {
							XSSFCell hssfCell = (XSSFCell) iterator.next();
							producto = crearProducto(producto, hssfCell, j, i);
						} else
							break;
						j++;
					}
					producto.setUsuario(usuario);
					producto.setOrganizacion(organizacion);
					productoService.saveImportProducto(producto);
				}
				i++;
			}
			productoNuevosExcel = productoService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productoNuevosExcel;
	}

	@SuppressWarnings("null")
	private Producto crearProducto(Producto itemBuilding,
			XSSFCell valorDePropiedad, int indiceColumna, int indiceRow) {
		String valor = String.valueOf(valorDePropiedad);
		String null_value = "null";
		try {
			switch (indiceColumna) {
			case 0:
				break;
			case 1:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setNombre(valor);
				else
					itemBuilding.setNombre(null_value);
				break;
			case 2:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					if (valor.length() > 1 && !valor.equals(" ")) {
						itemBuilding.setEnExistencia((int) Double
								.parseDouble(valor.replace(",", ".")));
					} else
						itemBuilding.setEnExistencia(0);
				break;
			case 3:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL"))) {
					if (valor.length() > 1 && !valor.equals(" "))
						itemBuilding.setPrecioCompra(Double.parseDouble(valor
								.replace(",", ".")));
				}

				else
					itemBuilding.setPrecioCompra(0D);
				break;
			case 4:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setPrecioVenta(Double.parseDouble(valor
							.replace(",", ".")));
				else
					itemBuilding.setPrecioVenta(0D);
				break;
			case 5:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setParamMarca(valor);
				else
					itemBuilding.setParamMarca(null_value);
				break;
			case 6:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setParamLugar(valor);
				else
					itemBuilding.setParamLugar(null_value);
				break;
			case 7:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setParamUnidad(valor);
				else
					itemBuilding.setParamUnidad(null_value);
				break;
			case 8:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setParamCategoria(valor);
				else
					itemBuilding.setParamCategoria(null_value);
				break;
			case 9:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL"))) {
					Calendar cal = Calendar.getInstance();
					String literalMonthRegexp = "\\d{1,2}-(?i)(ene|feb|mar|abr|may|jun|jul|ago|sep|oct|nov|dic)-\\d{4}";
					if (valor.matches(literalMonthRegexp)) {
						String[] divide = valor.replace("\"", "").split("-");

						cal = new StockUtils().convertirStringToCalendar(
								Integer.parseInt(divide[0]), getMes(divide[1]),
								Integer.parseInt(divide[2]));
					} else {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd/MM/YYYY");
						cal.setTime(sdf.parse(valor));
					}
					itemBuilding.setFechaActualizacion(cal);
				} else
					itemBuilding.setFechaActualizacion(Calendar.getInstance());

				break;
			case 10:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setCodigoBarras(valor);
				else
					itemBuilding.setCodigoBarras(null_value);
				break;
			case 11:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setMinimo((int) Double.parseDouble(valor
							.replace(",", ".")));
				else
					itemBuilding.setMinimo(0);
				break;
			case 12:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setDescripcion(valor);
				else
					itemBuilding.setDescripcion(null_value);
				break;
			case 13:
				if ((valor != null) && (!valor.isEmpty())
						&& (!valor.equalsIgnoreCase("NULL")))
					itemBuilding.setParamNaturaleza(valor);
				else
					itemBuilding.setParamNaturaleza(null_value);
				break;
			}
		} catch (Exception e) {
			System.out.println(indiceRow + " - " + indiceColumna);
			e.printStackTrace();
		}

		return itemBuilding;
	}

	private Integer getMes(String mes) {
		if (mes.contains("ene") || mes.contains("jan"))
			return 1;
		else if (mes.contains("feb"))
			return 2;
		else if (mes.contains("mar"))
			return 3;
		else if (mes.contains("abr") || mes.contains("apr"))
			return 4;
		else if (mes.contains("may"))
			return 5;
		else if (mes.contains("jun"))
			return 6;
		else if (mes.contains("jul"))
			return 7;
		else if (mes.contains("aug") || mes.contains("ago"))
			return 8;
		else if (mes.contains("sep"))
			return 9;
		else if (mes.contains("oct"))
			return 10;
		else if (mes.contains("nov"))
			return 11;
		else
			return 12;
	}


	public ProductoFiltro getProductoFilter() {
		return productoFilter;
	}


	

	@Command
	public void ctrlKeyClick(@ContextParam(ContextType.VIEW) Component view, @org.zkoss.bind.annotation.BindingParam("code") String ctrlKeyCode) {

		System.err.println(ctrlKeyCode);
	}

	@Command
	public void agregarImagen(){
		
	}

	@Command
	public void verImagenProducto(){
		Map<String, Object> inputParams = new HashMap();
		inputParams.put("executeMethod", "");
		inputParams.put("producto", producto);

		Window productoModalView = stockUtils.createModelDialogWithParams(
				"/modulos/productos/utils/visualizadorProducto.zul",
				inputParams);
		productoModalView.doModal();
	}
	
	@Command
	public void configurarImpresion(){
		Map<String, Object> inputParams = new HashMap();
		inputParams.put("executeMethod", "");
		inputParams.put("producto", producto);
		
		Window productoModalView = stockUtils.createModelDialogWithParams(
				"/modulos/productos/utils/configuracionImpresionCodigos.zul",
				inputParams);
		productoModalView.doModal();
	}
	
	//********************************
	private static final String footerMessage = "Un total de %d Productos";
	
	public ListModel<Producto> getProductoModel(){
		return new ListModelList<Producto>(productosDB);
	}

	@Command
    @NotifyChange({"productoModel", "footer"})
    public void changeFilter() {
		productosDB = listaGlobal.getFilterFoods(productoFilter);
    }
	
	public String getFooter() {
        return String.format(footerMessage, productosDB.size());
    }
	
	
	
}
