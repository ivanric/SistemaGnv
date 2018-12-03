package app.manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Service;

import app.models.DocumentoBeneficiario;
import app.models.Persona;






//@Service indica que la clase es un bean de la capa de negocio
@Service
public class ManejadorDocumentos{
	
	private JdbcTemplate db;
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	public class objDoc implements RowMapper<DocumentoBeneficiario>{
		@Override
		public DocumentoBeneficiario mapRow(ResultSet rs, int arg1) throws SQLException {
			DocumentoBeneficiario d= new DocumentoBeneficiario();
			d.setIddocb(rs.getInt("iddocb"));
			d.setNombre(rs.getString("nombre"));
			d.setEstado(rs.getInt("estado"));
			d.setTipoDoc(rs.getString("tipo"));
			return d;
	    }
	}




	
	//RESTROLES
	public List<DocumentoBeneficiario> ListarDocumentos(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="SELECT d.* FROM docBeneficiario d where (d.nombre LIKE ?) and (d.estado=? or ?=-1) ORDER BY d.iddocb ASC";
		return this.db.query(sql, new objDoc(),"%"+filtro+"%",estado,estado);
	}
	public List<Map<String,Object>> ListarD(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="SELECT d.* FROM docBeneficiario d where (d.nombre LIKE ?) and (d.estado=? or ?=-1) ORDER BY d.iddocb ASC";
		return this.db.queryForList(sql, new Object[]{},"%"+filtro+"%",estado,estado);
	}
	
	public boolean registrar(HttpServletRequest req,Persona xuser) {
		String sql="";
		int iddocb=generarIdDoc();
		try {
			sql="INSERT INTO docBeneficiario(iddocb,nombre,tipo) VALUES(?,?,?)";
			this.db.update(sql,iddocb,req.getParameter("nombre").toUpperCase(),req.getParameter("tipo"));
			return true;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public DocumentoBeneficiario datosModificar(HttpServletRequest req){
		String sql="";
		DocumentoBeneficiario d=null;
		int iddocb=Integer.parseInt(req.getParameter("iddocb"));
		System.out.println("iddocb: "+iddocb);
		try {
			sql="SELECT d.* FROM docBeneficiario d WHERE d.iddocb=?";
			d=this.db.queryForObject(sql,new objDoc(),iddocb);
		} catch (Exception e) {
			d=null;
		}
		return d;
	}
	public boolean modificar(HttpServletRequest req,Persona xuser){
		String sql="";
		int iddocb=Integer.parseInt(req.getParameter("iddocb"));
		
		try {
			sql="UPDATE docBeneficiario SET nombre=?,tipo=? WHERE iddocb=?";
			this.db.update(sql,req.getParameter("nombre").toUpperCase(),req.getParameter("tipo"),iddocb);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public boolean eliminar(Integer id){
		String sql="";
		try {
			sql="UPDATE docBeneficiario  SET estado=0 WHERE iddocb=?";
			int a=this.db.update(sql,id);
			this.db.update(sql,id);
			System.out.println("sql_elimino: "+a);
			if (a==1) {
				return true;	
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean habilitar(Integer id){
		String sql="";
		try {
			sql="UPDATE docBeneficiario SET estado=1 WHERE iddocb=?";
			int a=this.db.update(sql,id);
			this.db.update(sql,id);
			System.out.println("sql_habilito: "+a);
			if (a==1) {
				return true;	
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int generarIdDoc(){
		String sql="select COALESCE(max(iddocb),0)+1 as iddocb from docBeneficiario";
		return db.queryForObject(sql, Integer.class);
	}

	

}