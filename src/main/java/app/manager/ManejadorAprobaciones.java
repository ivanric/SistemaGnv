package app.manager;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ManejadorAprobaciones {
	private JdbcTemplate db;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	
	public int getTipoFinal(){
		String sql="SELECT idacc FROM accion WHERE codigo='af' and tipo='a'";
		return this.db.queryForObject(sql,Integer.class);
	}
	public int getTipoFinalTB(){
		String sql="SELECT idacc FROM accion WHERE codigo='af' and tipo='tb'";
		return this.db.queryForObject(sql,Integer.class);
	}
	
	public int verificarPolizaByPlaca(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			sql="SELECT COUNT(numeroPol) FROM  poliza WHERE placa=?";
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("ver????:"+data);
			if(data!=0){
				return data;	
			}else{
				return 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch");	
			return 0;
		}
		
	}
	
	
	public int insertarAprobacion(HttpServletRequest req,String login,int idSolt,String aprob[],int aprobarFinal){
		int estado=0;
		int idpol= generarIdPol();
		String sql="";
		String numeroPol=req.getParameter("numeroPol");	
		String idaseg=req.getParameter("idaseg");	
		String placa=req.getParameter("placa");	
		
		try {
			sql="INSERT INTO aprobacion(login,idsolt,idacc,tipoAcc) VALUES(?,?,?,?)";
			for (int i = 0; i < aprob.length; i++) {
				estado=this.db.update(sql,login,idSolt,Integer.parseInt(aprob[i]),"a");
			
			}
			if(aprobarFinal!=0) {
				sql="UPDATE solicitud SET aprobadoSiNo=? WHERE idsolt=?";
				estado=this.db.update(sql,1,idSolt);
			}

			
			//POLIZA
			if(verificarPolizaByPlaca(placa)==1) {
				sql="UPDATE poliza SET numeroPol=?,idaseg=?,login=? WHERE placa=?";
				this.db.update(sql,numeroPol,Integer.parseInt(idaseg),login,placa);
			}else {
				System.out.println("poliza nueva");
				sql="INSERT INTO poliza(idpol,numeroPol,idaseg,placa,login) VALUES(?,?,?,?,?)";
				this.db.update(sql,idpol,numeroPol,Integer.parseInt(idaseg),placa,login);	
			
			}
			
			sql="UPDATE solicitud SET idacc=? WHERE idsolt=?";
			estado=this.db.update(sql,aprob[aprob.length-1],idSolt);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
			estado=0;
		}
		return estado;
	}
	
	public boolean insertarAprobacionTB(String login,int idtrasl,int idsolt,String aprob[],int aprobarFinal){
		boolean estado=false;
		String sql="";
		try {
			sql="INSERT INTO aprobacion(login,idsolt,idacc,tipoAcc) VALUES(?,?,?,?)";
			for (int i = 0; i < aprob.length; i++) {
				this.db.update(sql,login,idsolt,Integer.parseInt(aprob[i]),"tb");
			
			}
			if(aprobarFinal!=0) {
				sql="UPDATE trasladoBeneficiario SET aprobadoSiNo=? WHERE idtrasl=?";
				this.db.update(sql,1,idtrasl);
			}
			estado=true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			estado=false;
		}
		return estado;
	}
	public int insertarPausaAprobacion(int idsolt,int idacc,String descripcion,String login){
		int pEstado=0;
		String sql="";
		int codPrimary=generarIdpausa();
		try {
			sql="INSERT INTO pausaAprobacion(idsolt,idacc,descripcion,login,idpaAp,tipoAcc) VALUES(?,?,?,?,?,?)";
			pEstado=this.db.update(sql,idsolt,idacc,descripcion,login,codPrimary,"a");
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println(e.getMessage());
			e.printStackTrace();
			pEstado=0;
		}
		return pEstado;
	}
	public int insertarPausaAprobacionTB(int idsolt,int idacc,String descripcion,String login){
		int pEstado=0;
		String sql="";
		int codPrimary=generarIdpausa();
		try {
			sql="INSERT INTO pausaAprobacion(idsolt,idacc,descripcion,login,idpaAp,tipoAcc) VALUES(?,?,?,?,?,?)";
			pEstado=this.db.update(sql,idsolt,idacc,descripcion,login,codPrimary,"tb");
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println(e.getMessage());
			e.printStackTrace();
			pEstado=0;
		}
		return pEstado;
	}
//	public int insertarPausaAprobacionTB(int idtrasl,int idacc,String descripcion,String login){
//		int pEstado=0;
//		String sql="";
//		int codPrimary=generarIdpausa();
//		try {
//			sql="INSERT INTO pausaAprobacion(idsolt,idacc,descripcion,login,idpTB) VALUES(?,?,?,?,?)";
//			pEstado=this.db.update(sql,idtrasl,idacc,descripcion,login,codPrimary);
//		} catch (Exception e) {
//			// TODO: handle exception 
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			pEstado=0;
//		}
//		return pEstado;
//	}
	
	//POLIZA
	
	
	
	public int generarIdpausa(){
		String sql="select COALESCE(max(idpaAp),0)+1 as idpaAp from pausaAprobacion";
		return db.queryForObject(sql, Integer.class);
	}
	public int generarIdPol(){
		String sql="select COALESCE(max(idpol),0)+1 as idpol from poliza";
		return db.queryForObject(sql, Integer.class);
	}
//	public int generarIdpausaTB(){
//		String sql="select COALESCE(max(idpTB),0)+1 as idpTB from pausaAprobacionTB";
//		return db.queryForObject(sql, Integer.class);
//	}
}
