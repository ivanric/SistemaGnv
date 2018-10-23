package app.models;

public class Accion {
	protected Integer idacc;
	protected String nombre;
	protected String codigo;
	protected Integer jerarquia;
	protected String tipo;
	protected Integer estado;
	public Accion() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Accion(Integer idacc, String nombre, String codigo, Integer jerarquia, String tipo, Integer estado) {
		super();
		this.idacc = idacc;
		this.nombre = nombre;
		this.codigo = codigo;
		this.jerarquia = jerarquia;
		this.tipo = tipo;
		this.estado = estado;
	}
	public Integer getIdacc() {
		return idacc;
	}
	public void setIdacc(Integer idacc) {
		this.idacc = idacc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getJerarquia() {
		return jerarquia;
	}
	public void setJerarquia(Integer jerarquia) {
		this.jerarquia = jerarquia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Accion [idacc=" + idacc + ", nombre=" + nombre + ", codigo=" + codigo + ", jerarquia=" + jerarquia
				+ ", tipo=" + tipo + ", estado=" + estado + "]";
	}
	
}
