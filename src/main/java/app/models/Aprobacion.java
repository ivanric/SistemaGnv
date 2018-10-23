package app.models;

import java.util.Date;

public class Aprobacion {
	protected Integer idsolt;
	protected Integer idacc;
	protected Date fechaAprob;
	protected String login;
	
	protected Accion accion;
	
	public Aprobacion() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public Aprobacion(Integer idsolt, Integer idacc, Date fechaAprob, String login, Accion accion) {
		super();
		this.idsolt = idsolt;
		this.idacc = idacc;
		this.fechaAprob = fechaAprob;
		this.login = login;
		this.accion = accion;
	}

	public Integer getIdsolt() {
		return idsolt;
	}

	public void setIdsolt(Integer idsolt) {
		this.idsolt = idsolt;
	}

	public Integer getIdacc() {
		return idacc;
	}

	public void setIdacc(Integer idacc) {
		this.idacc = idacc;
	}

	public Date getFechaAprob() {
		return fechaAprob;
	}

	public void setFechaAprob(Date fechaAprob) {
		this.fechaAprob = fechaAprob;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	@Override
	public String toString() {
		return "Aprobacion [idsolt=" + idsolt + ", idacc=" + idacc + ", fechaAprob=" + fechaAprob + ", login=" + login
				+ ", accion=" + accion + "]";
	}

	

}
