package ar.edu.iua.ingweb3.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="producto_id")
	private int id;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="precio")
	private double precio;
	
	@Column(name="en_stock")
	private boolean enStock;
	
	@Column(name="vencimiento")
	private Date vencimiento;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "producto_rubro", 
        joinColumns = { @JoinColumn(name = "producto_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "rubro_id") }
    )
// 	@ManyToOne
//	@JoinColumn(name="id_rubro", nullable=true or false)
	//  private Rubro rubro
	@JsonIgnoreProperties("productos")
	private List<Rubro> rubros;

	public Producto() {}

	public Producto(int id, String descripcion, double precio, boolean enStock, Date vencimiento, List<Rubro> rubros) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
		this.enStock = enStock;
		this.vencimiento = vencimiento;
		this.rubros = rubros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isEnStock() {
		return enStock;
	}

	public void setEnStock(boolean enStock) {
		this.enStock = enStock;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public List<Rubro> getRubros() {
		return rubros;
	}

	public void setRubros(List<Rubro> rubros) {
		this.rubros = rubros;
	}
		
}