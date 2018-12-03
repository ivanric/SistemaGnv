package app.models;

public class Aseguradora {
	protected Integer idaseg;
	protected String nombre,nit,direccion,telefono,fecha;
	protected Integer iddep;
	protected Integer idper;
	protected Integer estado;
	
	protected Persona persona;

	
	
	public Aseguradora() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public Aseguradora(Integer idaseg, String nombre, String nit, String direccion, String telefono, String fecha,
			Integer iddep, Integer idper, Integer estado, Persona persona) {
//		super();
		this.idaseg = idaseg;
		this.nombre = nombre;
		this.nit = nit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fecha = fecha;
		this.iddep = iddep;
		this.idper = idper;
		this.estado = estado;
		this.persona = persona;
	}

	public Integer getIdaseg() {
		return idaseg;
	}

	public void setIdaseg(Integer idaseg) {
		this.idaseg = idaseg;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getIddep() {
		return iddep;
	}

	public void setIddep(Integer iddep) {
		this.iddep = iddep;
	}

	public Integer getIdper() {
		return idper;
	}

	public void setIdper(Integer idper) {
		this.idper = idper;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Aseguradora [idaseg=" + idaseg + ", nombre=" + nombre + ", nit=" + nit + ", direccion=" + direccion
				+ ", telefono=" + telefono + ", fecha=" + fecha + ", iddep=" + iddep + ", idper=" + idper + ", estado="
				+ estado + ", persona=" + persona + "]";
	}
	
	
}
