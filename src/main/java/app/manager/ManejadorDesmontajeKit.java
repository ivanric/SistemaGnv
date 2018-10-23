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

import app.models.Beneficiario;
import app.models.DesmontajeKit;
import app.models.Persona;
import app.models.Solicitud;
import app.models.Taller;
import app.models.Vehiculo;


@Service
public class ManejadorDesmontajeKit {
	private JdbcTemplate db;
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	public class objDesmontajeKit implements RowMapper<DesmontajeKit>{
		@Override
		public DesmontajeKit mapRow(ResultSet rs, int arg1) throws SQLException {
			DesmontajeKit d= new DesmontajeKit();
			d.setIddes(rs.getInt("iddes"));
			d.setIdregistroKit(rs.getInt("idregistroKit"));
			d.setIdtall(rs.getInt("idtall"));
			d.setFecha(rs.getString("fecha"));
			d.setLogin(rs.getString("login"));
//			try {
//				d.setSolicitud(metSolicitud(rs.getInt("idsolt")));
//			} catch (Exception e) {
//				d.setSolicitud(null);
//			}
			try {
				d.setTaller(metTaller(rs.getInt("idtall")));
			} catch (Exception e) {
				d.setTaller(null);
			}
			return d;
	    }
	}
	//@aui empieza los objetos
	public class objTaller implements RowMapper<Taller>{
		@Override
		public Taller mapRow(ResultSet rs, int arg1) throws SQLException {
			Taller t= new Taller();
			t.setIdtall(rs.getInt("idtall"));
			t.setNombretall(rs.getString("nombretall"));
			t.setDireccion(rs.getString("direccion"));
			t.setFecha_registro(rs.getString("fecha_registro"));
			t.setEstado(rs.getInt("estado"));
			try {
				t.setPersona(metPersonaTall(rs.getInt("idper")));
			}catch (Exception e){
				t.setPersona(null);
			}
			return t;
	    }
	}
	public class objPersonaTall implements RowMapper<Persona>{
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
			//se quito usuario y login
			return p;
	    }
	}
	public class objSolicitud implements RowMapper<Solicitud>{
		@Override
		public Solicitud mapRow(ResultSet rs, int arg1) throws SQLException {
			Solicitud s= new Solicitud();
			s.setIdsolt(rs.getInt("idsolt"));
			s.setNumSolt(rs.getString("numSolt"));
			s.setFecha(rs.getString("fechaCreacion"));
			s.setObservaciones(rs.getString("observaciones"));
			s.setAprobadoSiNo(rs.getInt("aprobadoSiNo"));
			s.setLogin(rs.getString("login"));
			s.setEstado(rs.getInt("estado"));
			try {
				s.setVehiculo(metVehiculo(rs.getInt("idsolt")));
			} catch (Exception e) {
				s.setVehiculo(null);
			}
			try {
				s.setPersona(metObtPersona(rs.getInt("idsolt")));
			} catch (Exception e) {
				s.setPersona(null);
			}
			return s;
	    }
	}
	public class objetoPersona implements RowMapper<Persona>{
		@Override
		public Persona mapRow(ResultSet rs, int arg1) throws SQLException {
			Persona p= new Persona();
			p.setIdper(rs.getInt("idper"));
			p.setCi(rs.getString("ci"));
			p.setNombres(rs.getString("nombres"));
			p.setAp(rs.getString("ap"));
			p.setAm(rs.getString("am"));
			p.setGenero(rs.getString("genero"));
			p.setDireccion(rs.getString("direccion"));
			p.setEmail(rs.getString("email"));
			p.setFoto(rs.getString("foto"));
			p.setEstado(rs.getInt("estado"));
			try {
				p.setBeneficiario(metBeneficiario(rs.getInt("idper")));
			}catch (Exception e){
				p.setBeneficiario(null);
			}
			//se quito telefonos
			return p;
	    }
	}
	public class objBeneficiario implements RowMapper<Beneficiario>{
		@Override
		public Beneficiario mapRow(ResultSet rs, int arg1) throws SQLException {
			Beneficiario b= new Beneficiario();
			b.setIdben(rs.getInt("idben"));
			b.setInstitucion(rs.getString("institucion"));
			b.setDescripcion(rs.getString("descripcion"));
			b.setEstado(rs.getInt("estado"));
			b.setIdper(rs.getInt("idper"));
			//se quito documentos
			return b;
	    }
	}
	public class objVehiculo implements RowMapper<Vehiculo>{
		@Override
		public Vehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			Vehiculo v= new Vehiculo();
			v.setPlaca(rs.getString("placa"));
			v.setTjeta_prioridad(rs.getString("tjeta_prioridad"));
			v.setCilindrada(rs.getString("cilindrada"));
			v.setColor(rs.getString("color"));
			v.setNum_motor(rs.getString("num_motor"));
			v.setNum_chasis(rs.getString("num_chasis"));
			v.setKitGlpSiNo(rs.getInt("kitGlp"));
	
			//se quito opciones vehiculo
			return v;
	    }
	}


	/*QUERYS*/
	public List<DesmontajeKit> Lista(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		String sql="select d.* from desmontajeKit d join solicitud s on s.idsolt=d.idsolt JOIN taller t ON t.idtall=d.idtall JOIN benVehSolt bvs ON bvs.idsolt=s.idsolt JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 JOIN vehiculo veh on veh.placa=bvs.placa JOIN persona per ON per.idper=b.idper  where (concat(per.ap,' ',per.am,' ',per.nombres) LIKE ? or veh.placa LIKE ? ) ORDER BY d.iddes ASC";
		
		return this.db.query(sql, new objDesmontajeKit(),"%"+filtro+"%","%"+filtro+"%");
	}	
	/*METODOS*/
	public Beneficiario metBeneficiario(int idper){
		return this.db.queryForObject("select * from beneficiario where idper=?", new objBeneficiario(),idper);
	}
	public Solicitud metSolicitud(int idsolt){
		return this.db.queryForObject("select * from solicitud where idsolt=?", new objSolicitud(),idsolt);
	}
	public Vehiculo metVehiculo(int idsolt){
		return this.db.queryForObject("select v.* from vehiculo v,benVehSolt bvs,solicitud s,beneficiario b where v.placa=bvs.placa AND s.idsolt=bvs.idsolt AND b.idben=bvs.idben AND b.estado=1 AND s.idsolt=?", new objVehiculo(),idsolt);
	}
	public Persona metObtPersona(int idsolt){
		String sql="";
		try {
			sql="SELECT p.* FROM persona p JOIN beneficiario b ON b.idper=p.idper JOIN benVehSolt bvs ON bvs.idben=b.idben AND b.estado=1 JOIN solicitud s ON s.idsolt=bvs.idsolt WHERE  bvs.idsolt=?";
			return this.db.queryForObject(sql,new objetoPersona(),idsolt);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public Taller metTaller(int idtall){
		return this.db.queryForObject("select * from taller where idtall=?", new objTaller(),idtall);
	}
	public Persona metPersonaTall(int idper){
		String sql="SELECT p.* FROM persona p JOIN taller t ON t.idper=p.idper and p.idper=?";
		return this.db.queryForObject(sql,new objPersonaTall(),idper);
	}

}
