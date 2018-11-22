package app.models;

public class TrasladoKitVehiculo {
	protected Integer idtraslv,iddes,idsoltNueva;
	protected String fechaTraslado,login;
	protected Solicitud solicitudNueva;
	protected DesmontajeKit desmontajeKit;
	
	
	public TrasladoKitVehiculo() {
//		super();
		// TODO Auto-generated constructor stub
	}


	public TrasladoKitVehiculo(Integer idtraslv, Integer iddes, Integer idsoltNueva, String fechaTraslado, String login,
			Solicitud solicitudNueva, DesmontajeKit desmontajeKit) {
//		super();
		this.idtraslv = idtraslv;
		this.iddes = iddes;
		this.idsoltNueva = idsoltNueva;
		this.fechaTraslado = fechaTraslado;
		this.login = login;
		this.solicitudNueva = solicitudNueva;
		this.desmontajeKit = desmontajeKit;
	}


	public Integer getIdtraslv() {
		return idtraslv;
	}


	public void setIdtraslv(Integer idtraslv) {
		this.idtraslv = idtraslv;
	}


	public Integer getIddes() {
		return iddes;
	}


	public void setIddes(Integer iddes) {
		this.iddes = iddes;
	}


	public Integer getIdsoltNueva() {
		return idsoltNueva;
	}


	public void setIdsoltNueva(Integer idsoltNueva) {
		this.idsoltNueva = idsoltNueva;
	}


	public String getFechaTraslado() {
		return fechaTraslado;
	}


	public void setFechaTraslado(String fechaTraslado) {
		this.fechaTraslado = fechaTraslado;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public Solicitud getSolicitudNueva() {
		return solicitudNueva;
	}


	public void setSolicitudNueva(Solicitud solicitudNueva) {
		this.solicitudNueva = solicitudNueva;
	}


	public DesmontajeKit getDesmontajeKit() {
		return desmontajeKit;
	}


	public void setDesmontajeKit(DesmontajeKit desmontajeKit) {
		this.desmontajeKit = desmontajeKit;
	}


	@Override
	public String toString() {
		return "TrasladoKitVehiculo [idtraslv=" + idtraslv + ", iddes=" + iddes + ", idsoltNueva=" + idsoltNueva
				+ ", fechaTraslado=" + fechaTraslado + ", login=" + login + ", solicitudNueva=" + solicitudNueva
				+ ", desmontajeKit=" + desmontajeKit + "]";
	}
	
	
}
