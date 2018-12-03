package app.models;

public class DocumentoBeneficiario {
	protected Integer iddocb;
	protected String nombre;
	protected Integer estado;
	protected String tipoDoc;
	public DocumentoBeneficiario() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public DocumentoBeneficiario(Integer iddocb, String nombre, Integer estado, String tipoDoc) {
		super();
		this.iddocb = iddocb;
		this.nombre = nombre;
		this.estado = estado;
		this.tipoDoc = tipoDoc;
	}
	public Integer getIddocb() {
		return iddocb;
	}
	public void setIddocb(Integer iddocb) {
		this.iddocb = iddocb;
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
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	
}
