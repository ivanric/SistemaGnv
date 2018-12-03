package app.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Service;

import app.models.Aseguradora;
import app.models.Persona;

import app.models.Telefono;
import app.models.Usuario;
@Service
public class ManejadorAseguradoras {
	private JdbcTemplate db;
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	
	public class objAseguradora implements RowMapper<Aseguradora>{
		@Override
		public Aseguradora mapRow(ResultSet rs, int arg1) throws SQLException {
			Aseguradora a= new Aseguradora();
			a.setIdaseg(rs.getInt("idaseg"));
			a.setNombre(rs.getString("nombre"));
			a.setNit(rs.getString("nit"));
			a.setDireccion(rs.getString("direccion"));
			a.setTelefono(rs.getString("telefono"));
			a.setFecha(rs.getString("fecha"));
			a.setEstado(rs.getInt("estado"));
			a.setIdper(rs.getInt("idper"));
			try {
				a.setPersona(metPersona(rs.getInt("idper")));
			}catch (Exception e){
				a.setPersona(null);
			}
			return a;
	    }
	}
	public class objPersona implements RowMapper<Persona>{
		@Override
		public Persona mapRow(ResultSet rs, int arg1) throws SQLException {
			Persona p= new Persona();
			p.setIdper(rs.getInt("idper"));
			p.setCi(rs.getString("ci"));
			p.setCiCod(rs.getString("ciCod"));
			p.setNombres(rs.getString("nombres"));
			p.setAp(rs.getString("ap"));
			p.setAm(rs.getString("am"));
			p.setGenero(rs.getString("genero"));
			p.setDireccion(rs.getString("direccion"));
			p.setEmail(rs.getString("email"));
			p.setFoto(rs.getString("foto"));
			p.setEstado(rs.getInt("estado"));
			try {
				p.setUsuario(obtenerUsuario(rs.getInt("idper")));
			} catch (Exception e) {
				p.setUsuario(null);
			}
			try {
				p.setListaTelf(metListaTelf(rs.getInt("idper")));
			} catch (Exception e) {
				// TODO: handle exception
				p.setListaTelf(null);
			}
			return p;
	    }
	}
	public class objUsuario implements RowMapper<Usuario>{
		@Override
		public Usuario mapRow(ResultSet rs, int arg1) throws SQLException {
			Usuario u= new Usuario();
			u.setLogin(rs.getString("login"));
			u.setPassword(rs.getString("password"));
			u.setEstado(rs.getInt("estado"));
			u.setIdper(rs.getInt("idper"));
			return u;
	    }
	}
	public class objTelefono implements RowMapper<Telefono>{
		@Override
		public Telefono mapRow(ResultSet rs, int arg1) throws SQLException {
			Telefono t= new Telefono();
			t.setNumero(rs.getString("numero"));	
			return t;
	    }
	}
	public Persona metPersona(int idper){
		String sql="SELECT p.* FROM persona p JOIN aseguradora a ON a.idper=p.idper and p.idper=?";
		return this.db.queryForObject(sql,new objPersona(),idper);
	}
	public Usuario obtenerUsuario(int codper){
		return this.db.queryForObject("select * from usuario where idper=?", new objUsuario(),codper);
	}
	public List<Telefono> metListaTelf(int idper){
		return this.db.query("select * from telefono where idper=?", new objTelefono(),idper);
	}
	public int generarIdPer(){
		String sql="select COALESCE(max(idper),0)+1 as idper from persona";
		return db.queryForObject(sql, Integer.class);
	}
	public int generarIdAseg(){
		String sql="select COALESCE(max(idaseg),0)+1 as idaseg from aseguradora";
		return db.queryForObject(sql, Integer.class);
	}
	//REST_ASEGURADORAS
	public List<Aseguradora> ListarAseguradoras(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="SELECT a.* FROM aseguradora a where (a.nombre LIKE ?) and (a.estado=? or ?=-1) ORDER BY a.idaseg ASC";
		return this.db.query(sql, new objAseguradora(),"%"+filtro+"%",estado,estado);
	}
	public boolean registrar(HttpServletRequest req,Persona p){
		int idper= generarIdPer();
		int idaseg= generarIdAseg();
		String[] tel=req.getParameterValues("telefono[]");
		String sql="";
		try {
			sql="INSERT INTO persona(idper,ci,ciCod,nombres,ap,am,genero,direccion,email,estado) VALUES(?,?,?,?,?,?,?,?,?,?)";
			this.db.update(sql,idper,p.getCi(),p.getCiCod(),p.getNombres().toUpperCase(),p.getAp().toUpperCase(),p.getAm().toUpperCase(),p.getGenero(),p.getDireccion().toUpperCase(),p.getEmail(),1);
			sql="insert into telefono(numero,idper) values(?,?)";
			for (int i = 0; i < tel.length; i++) {
				if(!tel[i].equals("")) {
					this.db.update(sql,tel[i],idper);
				}
			}	
			sql="INSERT INTO aseguradora(idaseg,nombre,nit,direccion,telefono,estado,idper) VALUES(?,?,?,?,?,?,?)";
			this.db.update(sql,idaseg,req.getParameter("nombre").toUpperCase(),req.getParameter("nit").toUpperCase(),req.getParameter("direccion").toUpperCase(),req.getParameter("telefono"),1,idper);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public Aseguradora datosModificar(HttpServletRequest req){
		String sql="";
		Aseguradora t=null;
		int idaseg=Integer.parseInt(req.getParameter("idaseg"));
		System.out.println("idaseg: "+idaseg);
		try {
			sql="SELECT a.* FROM aseguradora a WHERE a.idaseg=?";
			t=this.db.queryForObject(sql,new objAseguradora(),idaseg);
		} catch (Exception e) {
			t=null;
		}
		return t;
	}
	public boolean modificar(HttpServletRequest req,Persona p,String [] tel){
		String sql="";
		try {
			sql="UPDATE persona SET nombres=?,ap=?,am=?,genero=?,direccion=?,email=? WHERE idper=?";
			this.db.update(sql,p.getNombres().toUpperCase(),p.getAp().toUpperCase(),p.getAm().toUpperCase(),p.getGenero(),p.getDireccion().toUpperCase(),p.getEmail(),req.getParameter("idper"));
			
			sql="delete from telefono where idper=?";
			this.db.update(sql,new Object[] {req.getParameter("idper")});
			
			sql="insert into telefono(numero,idper) values(?,?)";
			for (int i = 0; i < tel.length; i++) {
				if(!tel[i].equals("")) {
					this.db.update(sql,tel[i],req.getParameter("idper"));
				}
			}
			sql="UPDATE aseguradora SET nombre=?,nit=?,direccion=?,telefono=? WHERE idaseg=?";
			this.db.update(sql,req.getParameter("nombre").toUpperCase(),req.getParameter("nit").toUpperCase(),req.getParameter("direccion").toUpperCase(),req.getParameter("telefono"),req.getParameter("idaseg"));
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
			sql="UPDATE aseguradora  SET estado=0 WHERE idaseg=?";
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
			sql="UPDATE aseguradora SET estado=1 WHERE idaseg=?";
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
}
