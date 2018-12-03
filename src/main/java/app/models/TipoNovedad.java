package app.models;

public class TipoNovedad {
	protected Integer idtipnv;
	protected String nombre;
	protected Integer estado;
	public TipoNovedad() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public TipoNovedad(Integer idtipnv, String nombre, Integer estado) {
		super();
		this.idtipnv = idtipnv;
		this.nombre = nombre;
		this.estado = estado;
	}
	public Integer getIdtipnv() {
		return idtipnv;
	}
	public void setIdtipnv(Integer idtipnv) {
		this.idtipnv = idtipnv;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "TipoNovedad [idtipnv=" + idtipnv + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
	
	
}
