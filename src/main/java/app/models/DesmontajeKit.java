package app.models;

public class DesmontajeKit {
	private int iddes;
	private int idregistroKit;
	private int idtall;
	private String fecha;
	private String login;
	
	RegistroKit registroKit;
	Taller taller;
	public DesmontajeKit() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public DesmontajeKit(int iddes, int idregistroKit, int idtall, String fecha, String login, RegistroKit registroKit,
			Taller taller) {
		super();
		this.iddes = iddes;
		this.idregistroKit = idregistroKit;
		this.idtall = idtall;
		this.fecha = fecha;
		this.login = login;
		this.registroKit = registroKit;
		this.taller = taller;
	}
	public int getIddes() {
		return iddes;
	}
	public void setIddes(int iddes) {
		this.iddes = iddes;
	}
	public int getIdregistroKit() {
		return idregistroKit;
	}
	public void setIdregistroKit(int idregistroKit) {
		this.idregistroKit = idregistroKit;
	}
	public int getIdtall() {
		return idtall;
	}
	public void setIdtall(int idtall) {
		this.idtall = idtall;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public RegistroKit getRegistroKit() {
		return registroKit;
	}
	public void setRegistroKit(RegistroKit registroKit) {
		this.registroKit = registroKit;
	}
	public Taller getTaller() {
		return taller;
	}
	public void setTaller(Taller taller) {
		this.taller = taller;
	}
	@Override
	public String toString() {
		return "DesmontajeKit [iddes=" + iddes + ", idregistroKit=" + idregistroKit + ", idtall=" + idtall + ", fecha="
				+ fecha + ", login=" + login + ", registroKit=" + registroKit + ", taller=" + taller + "]";
	}
	
}
