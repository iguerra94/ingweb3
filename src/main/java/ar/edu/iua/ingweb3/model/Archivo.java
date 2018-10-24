package ar.edu.iua.ingweb3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Archivo {

	@Id
	@GeneratedValue
	private int id;

	@Column(name="nombre")
	private String nombre;
	
	@Column(name="length")
	private long length;
	
	@Column(name="mime")
	private String mime;

	// no se almacena este campo en la base de datos
	@Transient
	private String downloadUri;
	
	// se ignora en la json response
	@JsonIgnore
	@Lob
	private byte[] contenido;
	
	public Archivo() {}

	public Archivo(int id, String nombre, long length, String mime) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.length = length;
		this.mime = mime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getDownloadUri() {
		return downloadUri;
	}

	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}

}