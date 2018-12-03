package app.models;

public class IncumplimientoContrato {
	protected Integer idincl;
	protected String fechaIncumplio;
	protected String descripcion;
	protected Integer idsolt;
	protected String login;
	
	protected Solicitud solicitud;
	protected Persona persona; 
	public IncumplimientoContrato() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public IncumplimientoContrato(Integer idincl, String fechaIncumplio, String descripcion, Integer idsolt,
			String login, Solicitud solicitud, Persona persona) {
		super();
		this.idincl = idincl;
		this.fechaIncumplio = fechaIncumplio;
		this.descripcion = descripcion;
		this.idsolt = idsolt;
		this.login = login;
		this.solicitud = solicitud;
		this.persona = persona;
	}
	public Integer getIdincl() {
		return idincl;
	}
	public void setIdincl(Integer idincl) {
		this.idincl = idincl;
	}
	public String getFechaIncumplio() {
		return fechaIncumplio;
	}
	public void setFechaIncumplio(String fechaIncumplio) {
		this.fechaIncumplio = fechaIncumplio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getIdsolt() {
		return idsolt;
	}
	public void setIdsolt(Integer idsolt) {
		this.idsolt = idsolt;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	@Override
	public String toString() {
		return "IncumplimientoContrato [idincl=" + idincl + ", fechaIncumplio=" + fechaIncumplio + ", descripcion="
				+ descripcion + ", idsolt=" + idsolt + ", login=" + login + ", solicitud=" + solicitud + ", persona="
				+ persona + "]";
	}

	
	
}
