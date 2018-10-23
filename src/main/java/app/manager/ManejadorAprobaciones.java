package app.manager;

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
	
	public int insertarAprobacion(String login,int idSolt,String aprob[],int aprobarFinal){
		int estado=0;
		String sql="";
		try {
			sql="INSERT INTO aprobacion(login,idsolt,idacc,tipoAcc) VALUES(?,?,?,?)";
			for (int i = 0; i < aprob.length; i++) {
				estado=this.db.update(sql,login,idSolt,Integer.parseInt(aprob[i]),"a");
			
			}
			if(aprobarFinal!=0) {
				sql="UPDATE solicitud SET aprobadoSiNo=? WHERE idsolt=?";
				estado=this.db.update(sql,1,idSolt);
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
	public int generarIdpausa(){
		String sql="select COALESCE(max(idpaAp),0)+1 as idpaAp from pausaAprobacion";
		return db.queryForObject(sql, Integer.class);
	}
//	public int generarIdpausaTB(){
//		String sql="select COALESCE(max(idpTB),0)+1 as idpTB from pausaAprobacionTB";
//		return db.queryForObject(sql, Integer.class);
//	}
}
