package app.models;

public class Poliza {
	protected Integer idpol;
	protected String numeroPol;
	protected String fecha;
	protected Integer idaseg;
	protected String placa;
	protected String login;
	
	protected Aseguradora aseguradora;
	protected Vehiculo vehiculo;
	protected Persona persona;
	public Poliza() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public Poliza(Integer idpol, String numeroPol, String fecha, Integer idaseg, String placa, String login,
			Aseguradora aseguradora, Vehiculo vehiculo, Persona persona) {
//		super();
		this.idpol = idpol;
		this.numeroPol = numeroPol;
		this.fecha = fecha;
		this.idaseg = idaseg;
		this.placa = placa;
		this.login = login;
		this.aseguradora = aseguradora;
		this.vehiculo = vehiculo;
		this.persona = persona;
	}
	public Integer getIdpol() {
		return idpol;
	}
	public void setIdpol(Integer idpol) {
		this.idpol = idpol;
	}
	public String getNumeroPol() {
		return numeroPol;
	}
	public void setNumeroPol(String numeroPol) {
		this.numeroPol = numeroPol;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Integer getIdaseg() {
		return idaseg;
	}
	public void setIdaseg(Integer idaseg) {
		this.idaseg = idaseg;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Aseguradora getAseguradora() {
		return aseguradora;
	}
	public void setAseguradora(Aseguradora aseguradora) {
		this.aseguradora = aseguradora;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	@Override
	public String toString() {
		return "Poliza [idpol=" + idpol + ", numeroPol=" + numeroPol + ", fecha=" + fecha + ", idaseg=" + idaseg
				+ ", placa=" + placa + ", login=" + login + ", aseguradora=" + aseguradora + ", vehiculo=" + vehiculo
				+ ", persona=" + persona + ", getIdpol()=" + getIdpol() + ", getNumeroPol()=" + getNumeroPol()
				+ ", getFecha()=" + getFecha() + ", getIdaseg()=" + getIdaseg() + ", getPlaca()=" + getPlaca()
				+ ", getLogin()=" + getLogin() + ", getAseguradora()=" + getAseguradora() + ", getVehiculo()="
				+ getVehiculo() + ", getPersona()=" + getPersona() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
