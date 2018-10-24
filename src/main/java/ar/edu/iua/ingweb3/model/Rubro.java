package ar.edu.iua.ingweb3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rubro")
public class Rubro {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rubro_id")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@JsonIgnore
    @ManyToMany(mappedBy = "rubros")
	private List<Producto> productos;
	
	public Rubro() {}

	public Rubro(int id, String descripcion, List<Producto> productos) {
		this.id = id;
		this.descripcion = descripcion;
		this.productos = productos;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}