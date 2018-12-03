package app.models;

public class Novedad {
	protected Integer idnov;
	protected String fechaNovedad;
	protected String rechazoSiNo;
	protected String descripcionNovedad;
	protected String fechaInicial;
	protected String fechaFinal;
	protected Integer idsolt;
	protected Integer idtipnv;
	protected String login;
	
	protected Solicitud solicitud;
	protected TipoNovedad tipoNovedad;
	protected Persona persona;
	public Novedad() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Novedad(Integer idnov, String fechaNovedad, String rechazoSiNo, String descripcionNovedad,
			String fechaInicial, String fechaFinal, Integer idsolt, Integer idtipnv, String login, Solicitud solicitud,
			TipoNovedad tipoNovedad, Persona persona) {
		super();
		this.idnov = idnov;
		this.fechaNovedad = fechaNovedad;
		this.rechazoSiNo = rechazoSiNo;
		this.descripcionNovedad = descripcionNovedad;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.idsolt = idsolt;
		this.idtipnv = idtipnv;
		this.login = login;
		this.solicitud = solicitud;
		this.tipoNovedad = tipoNovedad;
		this.persona = persona;
	}
	public Integer getIdnov() {
		return idnov;
	}
	public void setIdnov(Integer idnov) {
		this.idnov = idnov;
	}
	public String getFechaNovedad() {
		return fechaNovedad;
	}
	public void setFechaNovedad(String fechaNovedad) {
		this.fechaNovedad = fechaNovedad;
	}
	public String getRechazoSiNo() {
		return rechazoSiNo;
	}
	public void setRechazoSiNo(String rechazoSiNo) {
		this.rechazoSiNo = rechazoSiNo;
	}
	public String getDescripcionNovedad() {
		return descripcionNovedad;
	}
	public void setDescripcionNovedad(String descripcionNovedad) {
		this.descripcionNovedad = descripcionNovedad;
	}
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public Integer getIdsolt() {
		return idsolt;
	}
	public void setIdsolt(Integer idsolt) {
		this.idsolt = idsolt;
	}
	public Integer getIdtipnv() {
		return idtipnv;
	}
	public void setIdtipnv(Integer idtipnv) {
		this.idtipnv = idtipnv;
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
	public TipoNovedad getTipoNovedad() {
		return tipoNovedad;
	}
	public void setTipoNovedad(TipoNovedad tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	@Override
	public String toString() {
		return "Novedad [idnov=" + idnov + ", fechaNovedad=" + fechaNovedad + ", rechazoSiNo=" + rechazoSiNo
				+ ", descripcionNovedad=" + descripcionNovedad + ", fechaInicial=" + fechaInicial + ", fechaFinal="
				+ fechaFinal + ", idsolt=" + idsolt + ", idtipnv=" + idtipnv + ", login=" + login + ", solicitud="
				+ solicitud + ", tipoNovedad=" + tipoNovedad + ", persona=" + persona + ", getIdnov()=" + getIdnov()
				+ ", getFechaNovedad()=" + getFechaNovedad() + ", getRechazoSiNo()=" + getRechazoSiNo()
				+ ", getDescripcionNovedad()=" + getDescripcionNovedad() + ", getFechaInicial()=" + getFechaInicial()
				+ ", getFechaFinal()=" + getFechaFinal() + ", getIdsolt()=" + getIdsolt() + ", getIdtipnv()="
				+ getIdtipnv() + ", getLogin()=" + getLogin() + ", getSolicitud()=" + getSolicitud()
				+ ", getTipoNovedad()=" + getTipoNovedad() + ", getPersona()=" + getPersona() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
