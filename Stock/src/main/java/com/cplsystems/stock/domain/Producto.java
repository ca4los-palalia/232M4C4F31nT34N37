package com.cplsystems.stock.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import org.zkoss.image.AImage;

import com.cplsystems.stock.app.utils.StockUtils;

@Entity
@Table
public class Producto implements Serializable {
	private static final long serialVersionUID = 8065024909123508528L;
	private Long idProducto;
	private Boolean activo;
	private String clave;
	private String codigoBarras;
	private String descripcion;
	private Integer enExistencia;
	private byte[] imagen;
	private Integer maximo;
	private Integer minimo;
	private String modelo;
	private String nombre;
	private Double precioCompra;
	private Double precioVenta;
	private Marca marca;
	private Categoria categoria;
	private ProductoNaturaleza productoNaturaleza;
	private Lugar lugar;
	private Usuarios usuario;
	private Unidad unidad;
	private Organizacion organizacion;
	private Calendar fechaActualizacion;
	private Date fechaActualizacionDate;
	private AImage codeBarAImage;
	private AImage imagenAImage;
	
	private String paramMarca;
	private String paramLugar;
	private String paramUnidad;
	private String paramCategoria;
	private String paramNaturaleza;
	private Integer ventaCantidad;
	private Float ventaSubtotal;
	private boolean generarButton;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto", nullable = false)
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	@Column
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Column
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	@Column
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column
	public Integer getEnExistencia() {
		return enExistencia;
	}

	public void setEnExistencia(Integer enExistencia) {
		this.enExistencia = enExistencia;
	}

	@Column
	@Lob
	public byte[] getImagen() {
		return imagen;
	}
	
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@OneToOne
	@JoinColumn(name = "marca")
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@Column
	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	@Column
	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	@Column
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Column
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column
	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	@Column
	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}
	
	@OneToOne
	@JoinColumn(name = "categoria")
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@OneToOne
	@JoinColumn(name = "productoNaturaleza")
	public ProductoNaturaleza getProductoNaturaleza() {
		return productoNaturaleza;
	}

	public void setProductoNaturaleza(ProductoNaturaleza productoNaturaleza) {
		this.productoNaturaleza = productoNaturaleza;
	}

	@OneToOne
	@JoinColumn(name = "lugar")
	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	@OneToOne
	@JoinColumn(name = "usuario")
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	@OneToOne
	@JoinColumn(name = "unidad")
	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	@OneToOne
	@JoinColumn(name = "organizacion")
	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	@Column
	public Calendar getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Calendar fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	@Transient
	public Date getFechaActualizacionDate() {
		if (this.fechaActualizacion != null) {
			this.fechaActualizacionDate = new StockUtils().convertirCalendarToDate(this.fechaActualizacion);
		}
		return this.fechaActualizacionDate;
	}

	public void setFechaActualizacionDate(Date fechaActualizacionDate) {
		this.fechaActualizacionDate = fechaActualizacionDate;
	}

	@Transient
	public String getParamLugar() {
		return paramLugar;
	}

	public void setParamLugar(String paramLugar) {
		this.paramLugar = paramLugar;
	}

	@Transient
	public String getParamUnidad() {
		return paramUnidad;
	}

	public void setParamUnidad(String paramUnidad) {
		this.paramUnidad = paramUnidad;
	}

	@Transient
	public String getParamCategoria() {
		return paramCategoria;
	}

	public void setParamCategoria(String paramCategoria) {
		this.paramCategoria = paramCategoria;
	}

	@Transient
	public String getParamNaturaleza() {
		return paramNaturaleza;
	}

	public void setParamNaturaleza(String paramNaturaleza) {
		this.paramNaturaleza = paramNaturaleza;
	}

	@Transient
	public AImage getCodeBarAImage() {
		
		if(codigoBarras != null && !codigoBarras.isEmpty() && codigoBarras.matches("[0-9]*")){
			byte[] imagenCodeBarByte = generarCodigoBarrasImagen(codigoBarras);
			try {
				codeBarAImage = new AImage("logoByte", imagenCodeBarByte);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return codeBarAImage;
	}
	
	@Transient
	public Integer getVentaCantidad() {
		return ventaCantidad;
	}

	public void setVentaCantidad(Integer ventaCantidad) {
		this.ventaCantidad = ventaCantidad;
	}

	@Transient
	public Float getVentaSubtotal() {
		if(ventaCantidad != null && precioVenta != null)
			ventaSubtotal = new Float(ventaCantidad * precioVenta);
		return ventaSubtotal;
	}

	public void setVentaSubtotal(Float ventaSubtotal) {
		this.ventaSubtotal = ventaSubtotal;
	}

	@Transient
	public boolean isGenerarButton() {
		if(codigoBarras == null || codigoBarras.isEmpty() || !codigoBarras.matches("[0-9]*"))
			generarButton = false;
		return generarButton;
	}

	public void setGenerarButton(boolean generarButton) {
		this.generarButton = generarButton;
	}

	@Transient
	public AImage getImagenAImage() {
		if (imagen != null) {
			try {
				imagenAImage = new AImage("imagenByte", imagen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			imagenAImage = getAImage();
		}
		return imagenAImage;
	}

	@Transient
	private byte[] generarCodigoBarrasImagen(String codeBar) {
		byte[] imageInByte = null; 

        try {
            if(codeBar.matches("[0-9]*")){
                
            	Barcode barcode = BarcodeFactory.createCode39(codeBar, false);
                barcode.setDrawingText(false);
                /*
                barcode.setBarHeight(50);
                barcode.setBarWidth(2);
                */
                //BufferedImage image = BarcodeImageHandler.getImage(BarcodeFactory.createCode39(codeBar, false));
                
                
                BufferedImage image = BarcodeImageHandler.getImage(barcode);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( image, "png", baos );
                baos.flush();
                imageInByte = baos.toByteArray();
                baos.close();
            /*
                BarcodeImageHandler.getImage(barcode)
                s = new ImageIcon(BarcodeImageHandler.getImage(barcode).ge);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageInByte;
    }
	
	@Transient
	private AImage getAImage() {
		AImage aimagen = null;
		File file = null;

		try {
			file = new File(getClass().getClassLoader().getResource("layout/product.png").toURI());
			aimagen = new AImage(file);
		} catch (Exception e) {
			System.out.println("El fichero " + file.getAbsolutePath() + " no se encuentra en el sistema");
		}
		return aimagen;
	}

	@Transient
	public String getParamMarca() {
		return paramMarca;
	}

	public void setParamMarca(String paramMarca) {
		this.paramMarca = paramMarca;
	}
	
	
	
}
