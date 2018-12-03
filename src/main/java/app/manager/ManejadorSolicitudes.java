package app.manager;

import java.net.IDN;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import app.models.Aprobacion;
import app.models.Aseguradora;
import app.models.Beneficiario;
import app.models.CombustibleVehiculo;
import app.models.DocumentoBeneficiario;
import app.models.IncumplimientoContrato;
import app.models.MarcaVehiculo;
import app.models.ModeloVehiculo;
import app.models.Novedad;
import app.models.Persona;
import app.models.Poliza;
import app.models.Servicio;
import app.models.Solicitud;
import app.models.Taller;
import app.models.Telefono;
import app.manager.ManejadorServicios.objPersona;
import app.manager.ManejadorServicios.objServicios;
import app.models.Accion;
import app.models.TipoMotorVehiculo;
import app.models.TipoNovedad;
import app.models.TipoServicioVehiculo;
import app.models.TipoVehiculo;
import app.models.Vehiculo;


@Service
public class ManejadorSolicitudes{
private JdbcTemplate db;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		db = new JdbcTemplate(dataSource);
	}
	
	
	//@aui empieza los objetos
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
			v.setEstado(rs.getInt("estado"));
			try {
				v.setCombustibleVehiculo(metConmbustibles(rs.getString("placa")));
			} catch (Exception e) {
				// TODO: handle exception
				v.setCombustibleVehiculo(null);
			}
			try {
				v.setTipoVehiculo(metTipoVehiculo(rs.getInt("idtipv")));
			} catch (Exception e) {
				v.setTipoVehiculo(null);
			}
			try {
				v.setMarcaVehiculo(metMarcaVehiculo(rs.getInt("idmarcv")));
			} catch (Exception e) {
				v.setMarcaVehiculo(null);
			}
			try {
				v.setTipoServicio(metTipoServicio(rs.getInt("idTipSv")));
			} catch (Exception e) {
				v.setTipoServicio(null);
			}
			try {
				v.setTipoMotor(metTipoMotor(rs.getInt("idtipoMotorVeh")));
			} catch (Exception e) {
				v.setTipoMotor(null);
			}
			try {
				v.setModeloVehiculo(metModeloVehiculo(rs.getInt("idmodv")));
			} catch (Exception e) {
				v.setModeloVehiculo(null);
			}

			return v;
	    }
	}
	
	public class objPoliza implements RowMapper<Poliza>{
		@Override
		public Poliza mapRow(ResultSet rs, int arg1) throws SQLException {
			Poliza p= new Poliza();
			p.setIdpol(rs.getInt("idpol"));
			p.setNumeroPol(rs.getString("numeroPol"));
			p.setFecha(rs.getString("fecha"));
			p.setIdaseg(rs.getInt("idaseg"));
			p.setPlaca(rs.getString("placa"));
			p.setLogin(rs.getString("login"));
			try {
				p.setAseguradora(metAseguradoraPol(rs.getInt("idaseg")));
			} catch (Exception e) {
				p.setAseguradora(null);
			}
			
			try {
				p.setVehiculo(metVehiculoPol(rs.getString("placa")));
			} catch (Exception e) {
				p.setVehiculo(null);
			}
			try {
				p.setPersona(metObtPersonaPol(rs.getInt("idpol")));
			} catch (Exception e) {
				p.setPersona(null);
			}
			return p;
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
			try {
				p.setListaTelf(metListaTelefonos(rs.getInt("idper")));
			} catch (Exception e) {
				// TODO: handle exception
			}
			return p;
	    }
	}
	public class objetoPersonaN implements RowMapper<Persona>{
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
				p.setListaTelf(metListaTelefonos(rs.getInt("idper")));
			} catch (Exception e) {
				// TODO: handle exception
			}
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
			try {
				b.setDocumentos(getDocumentos(rs.getInt("idben")));
			} catch (Exception e) {
				// TODO: handle exception
				b.setDocumentos(null);
			}
			return b;
	    }
	}
	public class objDocumento implements RowMapper<DocumentoBeneficiario>{
		@Override
		public DocumentoBeneficiario mapRow(ResultSet rs, int arg1) throws SQLException {
			DocumentoBeneficiario d= new DocumentoBeneficiario();
			d.setIddocb(rs.getInt("iddocb"));
			d.setNombre(rs.getString("nombre"));
			d.setEstado(rs.getInt("estado"));
			return d;
	    }
	}
	
	public class objCombustibleVehiculo implements RowMapper<CombustibleVehiculo>{
		@Override
		public CombustibleVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			CombustibleVehiculo c= new CombustibleVehiculo();
			c.setIdcomb(rs.getInt("idcomb"));
			c.setNombre(rs.getString("nombre"));
			c.setDetalle(rs.getString("detalle"));
			c.setEstado(rs.getInt("estado"));
			return c;
	    }
	}
	
	public class objTipoVehiculo implements RowMapper<TipoVehiculo>{
		@Override
		public TipoVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoVehiculo tp= new TipoVehiculo();
			tp.setIdtipv(rs.getInt("idtipv"));
			tp.setNombre(rs.getString("nombre"));
			tp.setEstado(rs.getInt("estado"));
			return tp;
	    }
	}
	
	public class objMarcaVehiculo implements RowMapper<MarcaVehiculo>{
		@Override
		public MarcaVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			MarcaVehiculo mv= new MarcaVehiculo();
			mv.setIdmarcv(rs.getInt("idmarcv"));
			mv.setNombre(rs.getString("nombre"));
			mv.setEstado(rs.getInt("estado"));
			return mv;
	    }
	}
	
	public class objTipoServicio implements RowMapper<TipoServicioVehiculo>{
		@Override
		public TipoServicioVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoServicioVehiculo ts= new TipoServicioVehiculo();
			ts.setIdTipSv(rs.getInt("idTipSv"));
			ts.setNombre(rs.getString("nombre"));
			ts.setEstado(rs.getInt("estado"));
			return ts;
	    }
	}
	public class objTipoMotor implements RowMapper<TipoMotorVehiculo>{
		@Override
		public TipoMotorVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoMotorVehiculo tm= new TipoMotorVehiculo();
			tm.setIdtipoMotorVeh(rs.getInt("idtipoMotorVeh"));
			tm.setNombre(rs.getString("nombre"));
			tm.setEstado(rs.getInt("estado"));
			return tm;
	    }
	}
	public class objModeloVehiculo implements RowMapper<ModeloVehiculo>{
		@Override
		public ModeloVehiculo mapRow(ResultSet rs, int arg1) throws SQLException {
			ModeloVehiculo mv= new ModeloVehiculo();
			mv.setIdmodv(rs.getInt("idmodv"));
			mv.setNombre(rs.getString("nombre"));
			mv.setEstado(rs.getInt("estado"));
			return mv;
	    }
	}
	
	public class objAprobacion implements RowMapper<Aprobacion>{
		@Override
		public Aprobacion mapRow(ResultSet rs, int arg1) throws SQLException {
			Aprobacion a= new Aprobacion();
			a.setIdsolt(rs.getInt("idsolt"));
			a.setIdacc(rs.getInt("idacc"));
			a.setFechaAprob(rs.getDate("fechaAprob"));
			a.setLogin(rs.getString("login"));
			try {
				a.setAccion(metTipoAprobador(rs.getInt("idacc")));
			} catch (Exception e) {
				// TODO: handle exception
				a.setAccion(null);
			}
			return a;
	    }
	}
//	public class objAprobacionTB implements RowMapper<AprobacionTB>{
//		@Override
//		public AprobacionTB mapRow(ResultSet rs, int arg1) throws SQLException {
//			AprobacionTB a= new AprobacionTB();
//			a.setIdtrasl(rs.getInt("idtrasl"));
//			a.setIdacc(rs.getInt("idacc"));
//			a.setFechaAprob(rs.getDate("fechaAprob"));
//			a.setLogin(rs.getString("login"));
//			try {
//				a.setAccion(metTipoAprobador(rs.getInt("idacc")));
//			} catch (Exception e) {
//				// TODO: handle exception
//				a.setAccion(null);
//			}
//			return a;
//	    }
//	}
	public class objAccion implements RowMapper<Accion>{
		@Override
		public Accion mapRow(ResultSet rs, int arg1) throws SQLException {
			Accion tpA= new Accion();
			tpA.setIdacc(rs.getInt("idacc"));
			tpA.setNombre(rs.getString("nombre"));
			tpA.setCodigo(rs.getString("codigo"));
			tpA.setJerarquia(rs.getInt("jerarquia"));
			tpA.setTipo(rs.getString("tipo"));
			tpA.setEstado(rs.getInt("estado"));
			return tpA;
	    }
	} 
	
	
	public class objTelefono implements RowMapper<Telefono>{
		@Override
		public Telefono mapRow(ResultSet rs, int arg1) throws SQLException {
			Telefono t= new Telefono();
			t.setIdper(rs.getInt("idper"));
			t.setNumero(rs.getString("numero"));	
			return t;
	    }
	}
	
	
	/*ASEGURADORA*/
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
			a.setIddep(rs.getInt("iddep"));
			a.setIdper(rs.getInt("idper"));
			a.setEstado(rs.getInt("estado"));
			try {
				a.setPersona(metPersonaAseg(rs.getInt("idper")));
			}catch (Exception e){
				a.setPersona(null);
			}
			return a;
	    }
	}
	
	/*INCUMPLIENTO CONTRATO*/
	public class objIContrato implements RowMapper<IncumplimientoContrato>{
		@Override
		public IncumplimientoContrato mapRow(ResultSet rs, int arg1) throws SQLException {
			IncumplimientoContrato i= new IncumplimientoContrato();
			i.setIdincl(rs.getInt("idincl"));
			i.setFechaIncumplio(rs.getString("fechaIncumplio"));
			i.setDescripcion(rs.getString("descripcion"));
			i.setIdsolt(rs.getInt("idsolt"));
			i.setLogin(rs.getString("login"));
			
			try {
				i.setSolicitud(metSolicitud(rs.getInt("idsolt")));
			} catch (Exception e) {
				i.setSolicitud(null);
			}
			try {
				i.setPersona(metPersonaIC("login"));
			} catch (Exception e) {
				// TODO: handle exception
				i.setPersona(null);
			}
			
			return i;
	    }
	}
	
	//NOVEDADES
	public class objTipoNovedad implements RowMapper<TipoNovedad>{
		@Override
		public TipoNovedad mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoNovedad tn= new TipoNovedad();
			tn.setIdtipnv(rs.getInt("idtipnv"));
			tn.setNombre(rs.getString("nombre"));
			tn.setEstado(rs.getInt("estado"));
			return tn;
	    }
	}
	//NOVEDADES
	public class objNovedad implements RowMapper<Novedad>{
		@Override
		public Novedad mapRow(ResultSet rs, int arg1) throws SQLException {
			Novedad n= new Novedad();
			n.setIdnov(rs.getInt("idnov"));
			n.setFechaNovedad(rs.getString("fechaNovedad"));
			n.setDescripcionNovedad(rs.getString("descripcionNovedad"));
			n.setFechaInicial(rs.getString("fechaInicial"));
			n.setFechaFinal(rs.getString("fechaFinal"));
			n.setIdsolt(rs.getInt("idsolt"));
			n.setIdtipnv(rs.getInt("idtipnv"));
			n.setLogin(rs.getString("login"));
			try {
				n.setSolicitud(metSolicitud(rs.getInt("idsolt")));
			} catch (Exception e) {
				n.setSolicitud(null);
			}
			try {
				n.setTipoNovedad(metTipoNovedad(rs.getInt("idtipnv")));
			} catch (Exception e) {
				// TODO: handle exception
				n.setTipoNovedad(null);
			}
			try {
				n.setPersona(metPersonaIC("login"));
			} catch (Exception e) {
				// TODO: handle exception
				n.setPersona(null);
			}
			return n;
	    }
	}
	
	
	public Persona metPersonaAseg(int idper){
		String sql="SELECT p.* FROM persona p JOIN aseguradora a ON a.idper=p.idper and p.idper=?";
		return this.db.queryForObject(sql,new objetoPersona(),idper);
	}
	public Beneficiario metBeneficiario(int idper){
		return this.db.queryForObject("select * from beneficiario where idper=?", new objBeneficiario(),idper);
	}
	public List<Telefono> metListaTelefonos(int idper){
		String sql="select * from telefono where idper=?";
		return this.db.query(sql,new objTelefono(),idper);
	}
	public List<DocumentoBeneficiario> getDocumentos(int idben){
		return this.db.query("SELECT d.* FROM docBeneficiario d,beneficiario b,bendoc bd WHERE d.iddocb=bd.iddocb and b.idben=bd.idben and b.idben=?", new objDocumento(),idben);
	}
	
	//$empesemos
//	public Vehiculo metVehiculo(String placa){
//		return this.db.queryForObject("select * from vehiculo where placa=?", new objVehiculo(),placa);
//	}
	public Vehiculo metVehiculo(int idsolt){
		return this.db.queryForObject("select v.* from vehiculo v,benVehSolt bvs,solicitud s,beneficiario b where v.placa=bvs.placa AND s.idsolt=bvs.idsolt AND b.idben=bvs.idben AND b.estado=1 AND s.idsolt=?", new objVehiculo(),idsolt);
	}
	public List<CombustibleVehiculo> metConmbustibles(String placa){
		return this.db.query("SELECT c.* FROM combustible c,vehiculo v,combveh cv WHERE c.idcomb=cv.idcomb and v.placa=cv.placa and v.placa=?", new objCombustibleVehiculo(),placa);
	}
	public TipoVehiculo metTipoVehiculo(int idtipv){
		return this.db.queryForObject("select tp.* from tipoVehiculo tp where idtipv=? and tp.estado=1", new objTipoVehiculo(),idtipv);
	}
	public MarcaVehiculo metMarcaVehiculo(int idmarcv){
		return this.db.queryForObject("select * from marcaVehiculo  where idmarcv=? and estado=1", new objMarcaVehiculo(),idmarcv);
	}
	public TipoServicioVehiculo metTipoServicio(int idTipSv){
		return this.db.queryForObject("select * from tipoServicioVehiculo where idTipSv=? and estado=1", new objTipoServicio(),idTipSv);
	}
	public TipoMotorVehiculo metTipoMotor(int idtipoMotorVeh){
		return this.db.queryForObject("select * from tipoMotorVehiculo where idtipoMotorVeh=? and estado=1", new objTipoMotor(),idtipoMotorVeh);
	}
	public ModeloVehiculo metModeloVehiculo(int idmodv){
		return this.db.queryForObject("select * from modeloVehiculo where idmodv=? and estado=1", new objModeloVehiculo(),idmodv);
	}
	
	//cambiar aqui por placa de parametro
//	public Persona metObtPersona(int idben){
//		String sql="";
//		try {
//			sql="SELECT p.* FROM persona p JOIN beneficiario b ON b.idper=p.idper and b.idben=?";
//			return this.db.queryForObject(sql,new objetoPersona(),idben);
//		}catch (Exception e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			return null;
//		}
//	}
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
	//Solicitudes
	public List<Solicitud> Listar(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		//String sql="select s.* from solicitud s join vehiculo v on s.placa=v.placa where  (v.placa LIKE ?) and (s.estado=? or ?=-1) ORDER BY s.idsolt ASC";
		String sql="select s.* from solicitud s JOIN benVehSolt bvs ON bvs.idsolt=s.idsolt join vehiculo v on bvs.placa=v.placa JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 where  (v.placa LIKE ?) and (s.estado=? or ?=-1) ORDER BY s.idsolt ASC";
		return this.db.query(sql, new objSolicitud(),"%"+filtro+"%",estado,estado);
	}
	
	public List<Vehiculo> ListarVeh(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
		String sql="select v.* from vehiculo v where  (v.placa LIKE ?) and (v.estado=? or ?=-1) ORDER BY v.placa ASC";
		return this.db.query(sql, new objVehiculo(),"%"+filtro+"%",estado,estado);
	}
	
	public List<DocumentoBeneficiario> listaDocumentos(){
		String sql="SELECT * FROM docBeneficiario WHERE estado=1 ORDER BY iddocb ASC";
		return this.db.query(sql,new objDocumento());
	}
	
	//Metodos al adicionar
	public int getAccion() {
		return this.db.queryForObject("select idacc from accion where tipo='s'",Integer.class);
	}
	public List<TipoVehiculo> tipoVehiculo(){
		String sql="select tp.* from tipoVehiculo tp where tp.estado=1";
		return this.db.query(sql,new objTipoVehiculo());
	}
	public List<MarcaVehiculo> marcaVehiculo(){
		String sql="select * from marcaVehiculo  where estado=1";
		return this.db.query(sql,new objMarcaVehiculo());
	}
	public List<ModeloVehiculo> modeloVehiculo(){
		String sql="select * from modeloVehiculo where estado=1";
		return this.db.query(sql, new objModeloVehiculo());
	}
	public List<TipoMotorVehiculo> tipoMotorVehiculo(){
		String sql="select * from tipoMotorVehiculo where estado=1";
		return this.db.query(sql, new objTipoMotor());
	}
	public List<TipoServicioVehiculo> tipoServicioVehiculo(){
		String sql="select * from tipoServicioVehiculo where estado=1";
		return this.db.query(sql,new objTipoServicio());
	}
	public List<CombustibleVehiculo> tipoCombustible(){
		String sql="SELECT * FROM combustible  WHERE estado=1";
		return this.db.query(sql,new objCombustibleVehiculo());
	}
	public int numeroSolicitud(){
		String sql="select COALESCE(max(idsolt),0)+1 as idsolt from solicitud";
		return db.queryForObject(sql, Integer.class);
	}
	public boolean verificarPlaca(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			sql="SELECT COUNT(placa) FROM vehiculo WHERE placa=? and estado=0";
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("ver????:"+data);
			if(data!=0){
				return true;	
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch");	
			return false;
		}
		
	}
	
	public boolean verificarPlacaMod(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			sql="SELECT COUNT(placa) FROM vehiculo WHERE placa=?";
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("ver????:"+data);
			if(data!=0){
				return true;	
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch");	
			return false;
		}
		
	}
	
	public int EstadoPlaca(String placa){
		System.out.println("entro sql_placa:"+placa);
		String sql="";
		try {
			//sql="SELECT COUNT(v.placa) FROM solicitud s, vehiculo v WHERE v.placa=s.placa and v.placa=? and v.estado=1";
			sql="SELECT COUNT(v.placa)FROM beneficiario b,vehiculo v,benVehSolt bvs WHERE b.idben=bvs.idben AND b.estado=1 AND v.placa=bvs.placa AND  v.placa=? and v.estado=1";
			int data=this.db.queryForObject(sql,Integer.class,placa);
			System.out.println("existe????:"+data);
			if(data!=0){
				return 1;	
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
	
	public Vehiculo DatosVehiculo(String placa){
		System.out.println("placa:"+placa);
		String sql="";
		try {
			sql="SELECT * FROM vehiculo WHERE placa=? and estado=0";
			return  this.db.queryForObject(sql,new objVehiculo(),placa);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch error");	
			return null;
		}
		
	}
	public Vehiculo getDatosVehiculoByPlaca(String placa){
		System.out.println("placa:"+placa);
		String sql="";
		try {
			sql="SELECT * FROM vehiculo WHERE placa=?";
			return  this.db.queryForObject(sql,new objVehiculo(),placa);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch error");	
			return null;
		}
		
	}
	
	public boolean modificarVeh(HttpServletRequest req,Vehiculo v, int [] combustible){
		String sql="";
		String placa_ant=req.getParameter("placa_hidden");
		try {
			
			BorrarCombustibles(placa_ant);
			
			sql="UPDATE vehiculo SET placa=?,tjeta_prioridad=?,cilindrada=?,color=?,num_motor=?,num_chasis=?,idtipv=?,idmarcv=?,idTipSv=?,idtipoMotorVeh=?,idmodv=? WHERE placa=?";
			this.db.update(sql,v.getPlaca().toUpperCase(),v.getTjeta_prioridad().toUpperCase(),v.getCilindrada().toUpperCase(),v.getColor().toUpperCase(),v.getNum_motor().toUpperCase(),v.getNum_chasis().toUpperCase(),v.getIdtipv(),v.getIdmarcv(),v.getIdTipSv(),v.getIdtipoMotorVeh(),v.getIdmodv(),placa_ant);
			
			sql="INSERT INTO combveh(placa,idcomb) VALUES(?,?)";
			for (int i = 0; i <combustible.length; i++){
				this.db.update(sql,v.getPlaca().toUpperCase(),combustible[i]);	
			}	
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public void BorrarCombustibles(String placa){
		String sql=" delete from combveh where placa= ?";
		this.db.update(sql,new Object[]{placa});
	}

	
	public Object[] registrar(HttpServletRequest req,Persona xuser,Vehiculo v,Solicitud s,int [] combustible,int idacc){
		int idsolt= generarIdSol();
		int idben=Integer.parseInt(req.getParameter("idben"));
		Object []Respuesta=new Object[2];
		String sql="";
		try {
			if(!verificarPlaca(v.getPlaca())){
				System.out.println("placa nueva");
				sql="INSERT INTO vehiculo(placa,tjeta_prioridad,cilindrada,color,num_motor,num_chasis,kitGlp,idtipv,idmarcv,idTipSv,idtipoMotorVeh,idmodv) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				this.db.update(sql,v.getPlaca().toUpperCase(),v.getTjeta_prioridad().toUpperCase(),v.getCilindrada().toUpperCase(),v.getColor().toUpperCase(),v.getNum_motor().toUpperCase(),v.getNum_chasis().toUpperCase(),v.getKitGlpSiNo(),v.getIdtipv(),v.getIdmarcv(),v.getIdTipSv(),v.getIdtipoMotorVeh(),v.getIdmodv());	
			}else{
				System.out.println("placa existe");
				sql="UPDATE vehiculo SET tjeta_prioridad=?,cilindrada=?,color=?,num_motor=?,num_chasis=?,idtipv=?,idmarcv=?,idTipSv=?,idtipoMotorVeh=?,idmodv=?,estado=? WHERE placa=?";
				this.db.update(sql,v.getTjeta_prioridad().toUpperCase(),v.getCilindrada().toUpperCase(),v.getColor().toUpperCase(),v.getNum_motor().toUpperCase(),v.getNum_chasis().toUpperCase(),v.getIdtipv(),v.getIdmarcv(),v.getIdTipSv(),v.getIdtipoMotorVeh(),v.getIdmodv(),1,v.getPlaca());
				BorrarCombustibles(v.getPlaca());
			}
			sql="INSERT INTO combveh(placa,idcomb) VALUES(?,?)";
			for (int i = 0; i <combustible.length; i++){
				this.db.update(sql,v.getPlaca().toUpperCase(),combustible[i]);	
			}	
			sql="INSERT INTO solicitud(idsolt,numSolt,observaciones,idacc,login) VALUES(?,?,?,?,?)"; 
			this.db.update(sql,idsolt,s.getNumSolt(),s.getObservaciones().toUpperCase(),idacc,xuser.getUsuario().getLogin());	
			
			//sql="insert INTO benveh(placa,idben) VALUES(?,?)";
			sql="insert INTO benVehSolt(placa,idben,idsolt) VALUES(?,?,?)";
			this.db.update(sql,v.getPlaca().toUpperCase(),idben,idsolt);
			 
			Respuesta[0]=true;
			Respuesta[1]=idsolt;
			return Respuesta;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("entro error");
			System.out.println("err:"+e.getMessage());
			e.printStackTrace();
			Respuesta[0]=false;
			return Respuesta;
		}
	}
	
	public boolean anular(Integer idsolt){
		String sql="";
		try {
			sql="UPDATE solicitud SET estado=0 WHERE idsolt=?";
			int a=this.db.update(sql,idsolt);
			System.out.println("sql_anulo: "+a);
			sql="UPDATE v SET v.estado=0 FROM vehiculo v JOIN benVehSolt bvs ON  bvs.placa=v.placa JOIN solicitud s ON s.idsolt=bvs.idsolt WHERE s.idsolt=?";
			this.db.update(sql,idsolt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/************************ANULAR SOLICITUD POR DESMONTAJE***********************/
	public boolean anularSoltDesmontaje(Integer idsolt){
		String sql="";
		try {
			sql="UPDATE solicitud SET estado=0 WHERE idsolt=?";
			int a=this.db.update(sql,idsolt);
			System.out.println("sql_anulo: "+a);
			sql="UPDATE v SET v.estado=0 FROM vehiculo v JOIN benVehSolt bvs ON  bvs.placa=v.placa JOIN solicitud s ON s.idsolt=bvs.idsolt WHERE s.idsolt=?";
			this.db.update(sql,idsolt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/***************************************************APROBACIONES****************************************************/
	public Accion metTipoAprobador(int idTipoAp){
		String sql="SELECT acc.* FROM accion acc,aprobacion a WHERE acc.idacc=a.idacc AND a.idacc=?";
		return this.db.queryForObject(sql,new objAccion(),idTipoAp);
	}
	
	public List<Solicitud> ListarSolAp(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		
		String sql="(select s.*,p.*\r\n" + 
				"from solicitud s join benVehSolt bvs on bvs.idsolt=s.idsolt JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 JOIN persona p ON p.idper=b.idper JOIN vehiculo v ON v.placa=bvs.placa \r\n" + 
				"where  ((s.numSolt LIKE ?) or (p.ci LIKE ?)) and s.estado=1)\r\n" + 
				"UNION\r\n" + 
				"(select s.*,p.*\r\n" + 
				"from solicitud s join benVehSolt bvs on bvs.idsolt=s.idsolt JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 JOIN persona p ON p.idper=b.idper JOIN vehiculo v ON v.placa=bvs.placa\r\n" + 
				"where  ((s.numSolt LIKE ?) or (p.ci LIKE ?)) and s.estado=0)ORDER BY s.idsolt ASC";
		return this.db.query(sql, new objSolicitud(),"%"+filtro+"%","%"+filtro+"%","%"+filtro+"%","%"+filtro+"%");
	}
	
	public List<Aprobacion> cargarAprobaciones(Solicitud s){
		String sql;
		try {
			sql="SELECT a.* FROM aprobacion a,solicitud s WHERE s.idsolt=a.idsolt and a.tipoAcc='a' AND s.idsolt=?";
			return this.db.query(sql, new objAprobacion(),s.getIdsolt());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	//POLIZA
	public List<Aseguradora> getAseguradoras(){
		String sql;
		try {
			sql="SELECT a.* FROM aseguradora a WHERE a.estado=1";
			return this.db.query(sql, new objAseguradora());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
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
	
	public Poliza DatosPoliza(String placa){
		System.out.println("placa:"+placa);
		String sql="";
		try {
			sql="SELECT p.* FROM poliza p,vehiculo v WHERE v.placa=p.placa and  v.placa=? and v.estado=1";
			return  this.db.queryForObject(sql,new objPoliza(),placa);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("entro catch error");	
			return null;
		}
		
	}
	public Aseguradora metAseguradoraPol(int id){
		return this.db.queryForObject("select a.* from aseguradora a,poliza p where a.idaseg=p.idaseg AND a.idaseg=?", new objAseguradora(),id);
	}
	public Vehiculo metVehiculoPol(String placa){
		return this.db.queryForObject("select v.* from vehiculo v,poliza p where v.placa=p.placa AND v.placa=?", new objVehiculo(),placa);
	}
	public Persona metObtPersonaPol(int id){
		String sql="";
		try {
			sql="SELECT p.* FROM persona p JOIN usuario u ON u.idper=p.idper JOIN poliza pol ON pol.login=u.login WHERE  pol.idpol=?";
			return this.db.queryForObject(sql,new objetoPersona(),id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	//FIN POLIZA
	
	public List<Aprobacion> cargarAprobacionesTB(int idsolt){
		String sql;
		try {
			sql="SELECT a.* FROM aprobacion a,solicitud s WHERE s.idsolt=a.idsolt and a.tipoAcc='tb' AND s.idsolt=?";
			return this.db.query(sql, new objAprobacion(),idsolt);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	public Solicitud ListarSolById(HttpServletRequest req){
		String idsolt=req.getParameter("idsolt");
		String sql="select s.* from solicitud s where s.idsolt=? and s.estado=1 ORDER BY s.idsolt ASC";
		return this.db.queryForObject(sql, new objSolicitud(),idsolt);
	}

	public List<Accion> ListaTipoAprob(){
		String sql="";
		try {
			sql="select * from accion where tipo='a' and estado=1 ORDER BY jerarquia ASC";
			return this.db.query(sql, new objAccion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
			// TODO: handle exception
		}
		
		
	}
	
	public List<Accion> ListaTipoAprobTB(){
		String sql="";
		try {
			sql="select * from accion where tipo='tb' and estado=1 ORDER BY jerarquia ASC";
			return this.db.query(sql, new objAccion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
			// TODO: handle exception
		}
		
		
	}
	
	public Map<String, Object> UsuarioCreadorSolt(String login){
		String sql="select p.* from persona p, usuario u where u.idper=p.idper and u.login=?";
		return this.db.queryForMap(sql,new Object[] {login});
	}
	public List<Map<String, Object>> ListaTelefonos(int idper){
		String sql="";
		try {
			sql="select * from telefono where idper=?";
			return this.db.queryForList(sql,new Object[] {idper});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	public List<Map<String, Object>> ListaPausaAprob(HttpServletRequest req){
		String idsolt=req.getParameter("idsolt");
		String sql="select * from pausaAprobacion WHERE idsolt=? and tipoAcc='a'";
		return this.db.queryForList(sql,new Object[] {Integer.parseInt(idsolt)});
	}
	
	public List<Map<String, Object>> ListaPausaAprobTB(int id){
		String sql="select * from pausaAprobacion WHERE idsolt=? and tipoAcc='tb'";
		return this.db.queryForList(sql,new Object[] {id});
	}
	
	
	public List<Telefono> ListaTelf(int idsolt){
		String sql="SELECT t.* FROM telefono t,persona p,beneficiario b,solicitud s,benVehSolt bvs WHERE t.idper=p.idper and p.idper=b.idper AND b.idben=bvs.idben AND b.estado=1 AND s.idsolt=bvs.idsolt AND s.idsolt=?";
		return this.db.query(sql,new objTelefono(),idsolt);
	}
	public Solicitud verSolicitud(int idsolt) {
		String sql="select * from solicitud where idsolt=?";
		return this.db.queryForObject(sql,new objSolicitud(),idsolt);
	}
	
	/*ORDEN DE SERVICIO*/
	public List<Solicitud> FiltroSolicitudOS(String cadena){
		//String sql="SELECT s.* FROM solicitud s,beneficiario b,persona p WHERE s.idben=b.idben and s.estado=1  and aprobadoSiNo=1 and b.idper=p.idper and (s.numSolt LIKE ? or p.ci LIKE ?) and s.idsolt NOT IN (SELECT od.idsolt FROM ordenServicio od WHERE od.idsolt=s.idsolt)";
		String sql="SELECT s.* FROM solicitud s,beneficiario b,persona p,benVehSolt bvs WHERE bvs.idben=b.idben AND b.estado=1 AND bvs.idsolt=s.idsolt and s.estado=1 and s.aprobadoSiNo=1 and b.idper=p.idper and (s.numSolt LIKE ? or p.ci LIKE ?) and s.idsolt NOT IN (SELECT od.idsolt FROM ordenServicio od WHERE od.idsolt=s.idsolt)";
		return this.db.query(sql, new objSolicitud(),'%'+cadena+'%','%'+cadena+'%');
	}
	
	public int generarIdSol(){
		String sql="select COALESCE(max(idsolt),0)+1 as idsolt from solicitud";
		return db.queryForObject(sql, Integer.class);
	}
	public List<CombustibleVehiculo> listaCombustible(){
		String sql="SELECT * FROM combustible WHERE estado=1 ORDER BY idcomb ASC";
		return this.db.query(sql,new objCombustibleVehiculo());
	}

	
	/*ORDEN SERVICIO*/
	public Solicitud metSolicitud(int idsolt){
		return this.db.queryForObject("select * from solicitud where idsolt=?", new objSolicitud(),idsolt);
	}
	
	public Solicitud getSoltByOrdServ(int id) {
		return this.db.queryForObject("select s.* from solicitud s,ordenServicio os where os.idsolt=s.idsolt and os.idordserv=?", new objSolicitud(),id);
	}
	
	/*TRASLADO KIT VEHICULO*/
	public Solicitud getSoltByTraslVeh(int id) {
		return this.db.queryForObject("select s.* from solicitud s,trasladoKitVehiculo tvk where tvk.idsoltNueva=s.idsolt and tvk.idtraslv=?", new objSolicitud(),id);
	}
	
	/*INCUMPLIENTO CONTRATO*/
	public List<IncumplimientoContrato> ListarIContratos(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		String sql="select ic.* from incumplimientoContrato  ic join solicitud s ON ic.idsolt=s.idsolt JOIN benVehSolt bvs ON bvs.idsolt=s.idsolt join vehiculo v on bvs.placa=v.placa JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 join persona p on p.idper=b.idper where  (concat(p.ap,' ',p.am,' ',p.nombres) LIKE ? or p.ci LIKE ? or v.placa LIKE ?)  ORDER BY ic.idincl ASC";
		return this.db.query(sql, new objIContrato(),"%"+filtro+"%","%"+filtro+"%","%"+filtro+"%");
	}
	public Persona metPersonaIC(String login){
		String sql="SELECT p.* FROM persona p JOIN usuario u ON u.idper=p.idper and u.login=?";
		return this.db.queryForObject(sql,new objetoPersonaN(),login);
	}
	public Poliza metPolizaIC(String placa){
		String sql="SELECT p.* FROM poliza p JOIN vehiculo v ON v.placa=p.placa and p.placa=?";
		return this.db.queryForObject(sql,new objPoliza(),placa);
	}
	public List<Solicitud> FiltroSolicitudIC(String cadena){
		//String sql="SELECT s.* FROM solicitud s,beneficiario b,persona p WHERE s.idben=b.idben and s.estado=1  and aprobadoSiNo=1 and b.idper=p.idper and (s.numSolt LIKE ? or p.ci LIKE ?) and s.idsolt NOT IN (SELECT od.idsolt FROM ordenServicio od WHERE od.idsolt=s.idsolt)";
		String sql="SELECT s.* FROM solicitud s,beneficiario b,persona p,benVehSolt bvs,ordenServicio os,registroKit rk WHERE bvs.idben=b.idben AND b.estado=1 AND bvs.idsolt=s.idsolt and s.estado=1 and s.aprobadoSiNo=1 and b.idper=p.idper and s.idsolt=os.idsolt AND os.idordserv=rk.idordserv and (s.numSolt LIKE ? or p.ci LIKE ?) and s.idsolt NOT IN (SELECT ic.idsolt FROM incumplimientoContrato ic WHERE ic.idsolt=s.idsolt)";
		return this.db.query(sql, new objSolicitud(),'%'+cadena+'%','%'+cadena+'%');
	}
	public int getAccionIC() {
		return this.db.queryForObject("select idacc from accion where tipo='ic'",Integer.class);
	}
	public int generarIdincl(){
		String sql="select COALESCE(max(idincl),0)+1 as idincl from incumplimientoContrato";
		return db.queryForObject(sql, Integer.class);
	}
	public int generarIdPol(){
		String sql="select COALESCE(max(idpol),0)+1 as idpol from poliza";
		return db.queryForObject(sql, Integer.class);
	}
	public Object[] registrarIC(HttpServletRequest req,Persona xuser,int idacc){
		System.out.println("entro");
		int idincl= generarIdincl();
		int idpol= generarIdPol();
		System.out.println("idincl:"+idincl+" idpol:"+idpol);
		String login=xuser.getUsuario().getLogin();
		int idsolt=Integer.parseInt(req.getParameter("idsolt"));
		String descripcion=req.getParameter("descripcion");
		String placa=req.getParameter("placa");
		
		String numeroPol=req.getParameter("numeroPol");
		int idaseg=Integer.parseInt(req.getParameter("idaseg"));
		System.out.println("idsolt:"+idsolt+" desc:"+descripcion+" placa: "+placa+" numeroPol:"+numeroPol+" idaseg;"+idaseg);
		
		Object []Respuesta=new Object[2];
		String sql="";
		try {

			sql="INSERT INTO incumplimientoContrato(idincl,descripcion,idsolt,login) VALUES(?,?,?,?)"; 
			this.db.update(sql,idincl,descripcion,idsolt,login);	
			
			//POLIZA
			if(verificarPolizaByPlaca(placa)==1) {
				sql="UPDATE poliza SET numeroPol=?,idaseg=?,login=? WHERE placa=?";
				this.db.update(sql,numeroPol,idaseg,login,placa);
			}else {
				System.out.println("poliza nueva");
				sql="INSERT INTO poliza(idpol,numeroPol,idaseg,placa,login) VALUES(?,?,?,?,?)";
				this.db.update(sql,idpol,numeroPol,idaseg,placa,login);	
			
			}
			
			sql="UPDATE solicitud SET idacc=? WHERE idsolt=?";
			this.db.update(sql,idacc,idsolt);
		
			//ANULAR SOLT
			anular(idsolt);
			Respuesta[0]=true;
			Respuesta[1]=idincl;
			return Respuesta;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("entro error");
			System.out.println("err:"+e.getMessage());
			e.printStackTrace();
			Respuesta[0]=false;
			return Respuesta;
		}
	}
	
	public IncumplimientoContrato verIContrato(int idincl) {
		String sql="select * from incumplimientoContrato where idincl=?";
		return this.db.queryForObject(sql,new objIContrato(),idincl);
	}
	//FIN INCUMPLIMIENTO CONTRATO
	
	
	/***************************NOVEDADES***************************/
	public TipoNovedad metTipoNovedad(int idtipnv){
//		String sql="select tn.* from tipoNovedad tn,novedad n  where tn.idtipnv=n.idtipnv and tn.idtipnv=? and tn.estado=1";
		String sql="select tn.* from tipoNovedad tn where tn.idtipnv=? and tn.estado=1";
		return this.db.queryForObject(sql,new objTipoNovedad(),idtipnv);
	}

	public List<Novedad> ListarNovedades(HttpServletRequest req){
		String filtro=req.getParameter("filtro");
		int estado=Integer.parseInt(req.getParameter("estado"));
//		String sql="select nov.* from novedad nov JOIN solicitud s ON s.idsolt=nov.idsolt JOIN benVehSolt bvs ON bvs.idsolt=s.idsolt join vehiculo v on bvs.placa=v.placa JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1 JOIN ordenServicio os ON os.idsolt=s.idsolt JOIN registroKit rk ON rk.idordserv=os.idordserv   where  (v.placa LIKE ? or s.numSolt LIKE ?) and (s.estado=? or ?=-1) ORDER BY nov.idnov ASC";
		String sql="select nov.* from novedad nov JOIN solicitud s ON s.idsolt=nov.idsolt JOIN benVehSolt bvs ON bvs.idsolt=s.idsolt join vehiculo v on bvs.placa=v.placa JOIN beneficiario b ON b.idben=bvs.idben AND b.estado=1  where  (v.placa LIKE ? or s.numSolt LIKE ?) and (s.estado=? or ?=-1) ORDER BY nov.idnov ASC";
		return this.db.query(sql, new objNovedad(),"%"+filtro+"%","%"+filtro+"%",estado,estado);
	}
	public List<TipoNovedad> getTipoNovedades(){
		String sql="select * from tipoNovedad  where estado=1";
		return this.db.query(sql,new objTipoNovedad());
	}
	
	public List<Solicitud> FiltroSolicitudNov(String cadena){//LISTA TODAS LAS SOLICITUDES INICIALES Y TERMINADAS LAS CONVERSIONES
		String sql="SELECT s.* FROM solicitud s,beneficiario b,persona p,benVehSolt bvs WHERE bvs.idben=b.idben AND b.estado=1 AND bvs.idsolt=s.idsolt and s.estado=1 and b.idper=p.idper  and (s.numSolt LIKE ? or p.ci LIKE ?) ";
		return this.db.query(sql, new objSolicitud(),'%'+cadena+'%','%'+cadena+'%');
	}
	public int generarIdnov(){
		String sql="select COALESCE(max(idnov),0)+1 as idnov from novedad";
		return db.queryForObject(sql, Integer.class);
	}
	public Object[] registrarNov(HttpServletRequest req,Persona xuser){
		System.out.println("entro");
		int idnov= generarIdnov();
		String login=xuser.getUsuario().getLogin();
		int idsolt=Integer.parseInt(req.getParameter("idsolt"));
		int idtipnv=Integer.parseInt(req.getParameter("idtipnv"));
		String descripcion=req.getParameter("description");
		System.out.println("descripcion:"+descripcion==null);
		String fechaInicial=req.getParameter("fechaInicial");
		String fechaFinal=req.getParameter("fechaFinal");
		
		Object []Respuesta=new Object[2];
		String sql="";
		try {

			sql="INSERT INTO novedad(idnov,descripcionNovedad,fechaInicial,fechaFinal,idsolt,idtipnv,login) VALUES(?,?,?,?,?,?,?)"; 
			this.db.update(sql,idnov,descripcion,fechaInicial,fechaFinal,idsolt,idtipnv,login);	
		
			//ANULAR SOLT
			anular(idsolt);
			Respuesta[0]=true;
			Respuesta[1]=idnov;
			return Respuesta;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("entro error");
			System.out.println("err:"+e.getMessage());
			e.printStackTrace();
			Respuesta[0]=false;
			return Respuesta;
		}
	}
	
	public boolean habilitarSolicitud(Integer id,String placa){
		String sql="";
		try {
			sql="UPDATE vehiculo SET estado=1 WHERE placa=?";
			int a=this.db.update(sql,placa);
			
			sql="UPDATE solicitud SET estado=1 WHERE idsolt=?";
			a=this.db.update(sql,id);
			
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
	/*REPORTES*/
	//Solicitudes
	public List<Map<String, Object>> ListarReportes(){		
		String sql="select * from reportes";
		return this.db.queryForList(sql, new Object[] {});
	}

}
