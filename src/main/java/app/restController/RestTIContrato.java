package app.restController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorServicios;
import app.manager.ManejadorSolicitudes;
import app.models.CombustibleVehiculo;
import app.models.DocumentoBeneficiario;
import app.models.IncumplimientoContrato;
import app.models.Persona;
import app.models.Solicitud;
import app.models.Telefono;
import app.models.TrasladoKitVehiculo;
import app.models.Vehiculo;
import app.utilidades.GeneradorReportes;
import app.utilidades.URIS;


@RequestMapping("/RestTIContrato/")
@RestController
public class RestTIContrato {
	
	@Autowired
	ManejadorSolicitudes manejadorSolicitudes;
	@Autowired
	ManejadorServicios manejadorServicios;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<IncumplimientoContrato>> listarServicio(HttpServletRequest req,HttpServletResponse res){	
		List<IncumplimientoContrato> resp=this.manejadorSolicitudes.ListarIContratos(req);
		for (int i = 0; i <resp.size(); i++) {
			resp.get(i).getSolicitud().getVehiculo().setPoliza(this.manejadorSolicitudes.metPolizaIC(resp.get(i).getSolicitud().getVehiculo().getPlaca()));
//			resp.get(i).setPersona(this.manejadorSolicitudes.metPersonaIC(resp.get(i).getLogin()));
		}
		System.out.println("listaResp: "+resp.toString());
		return new ResponseEntity<List<IncumplimientoContrato>>(resp,HttpStatus.OK);
	}
	
	@RequestMapping(value="FiltroSolicitudIC")
	public ResponseEntity<List<Solicitud>> FiltroSolicitud(HttpServletRequest req,HttpServletResponse res){
		String texto=req.getParameter("texto");
		System.out.println("texto: "+texto);
		 
		List<Solicitud> solt=null;
		try {
			solt=this.manejadorSolicitudes.FiltroSolicitudIC(req.getParameter("texto"));
			System.out.println("TAM: "+solt.size());
			System.out.println("KU: "+solt);
		} catch (Exception e) {
			solt=null;
		}
		return new ResponseEntity<List<Solicitud>>(solt,HttpStatus.OK);
	}
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		int idacc=this.manejadorSolicitudes.getAccionIC();
		System.out.println("idacc: "+idacc);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		System.out.println("idsolt: "+req.getParameter("idsolt"));
		try {
		
			Object[] Resp=this.manejadorSolicitudes.registrarIC(req,xuser,idacc);
			respuesta.put("estado", Resp[0]);
			respuesta.put("idincl", Integer.parseInt(Resp[1].toString()));
			System.out.println("resp:"+respuesta.toString());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println("error cach"+e.getMessage());
			respuesta.put("estado",false);
		}
//		respuesta.put("estado",false);
		return respuesta;
	}
	@RequestMapping(value="Ver")
	public ResponseEntity<List<?>> Ver(HttpServletRequest req,HttpServletResponse res){
		List<Object> lista=new ArrayList<>();

		String idincl=req.getParameter("idincl");
		System.out.println("idincl: "+idincl);

		IncumplimientoContrato ic=this.manejadorSolicitudes.verIContrato(Integer.parseInt(idincl));
		ic.getSolicitud().getVehiculo().setPoliza(this.manejadorSolicitudes.metPolizaIC(ic.getSolicitud().getVehiculo().getPlaca()));
		lista.add(ic);

		return new ResponseEntity<List<?>>(lista,HttpStatus.OK);
	}
	@Autowired
	DataSource dataSource;
	@RequestMapping("ImprimirIC")
	public  void verOS(HttpServletResponse res,HttpServletRequest req){
		URIS uris=new URIS();
//		Persona us=(Persona)req.getSession(true).getAttribute("xusuario");
//		String Tramitador=us.getAp().toUpperCase()+" "+us.getAm().toUpperCase()+" "+us.getNombres().toUpperCase();
		String idincl=req.getParameter("idincl");
		String ListaTelefonosBeneficiario="";
		

		
		List<Telefono> ListaTelfBen=this.manejadorServicios.ListaTelfIC(Integer.parseInt(idincl));
//		System.out.println("ListaTelefonosTall: "+ListaTelfBen.toString());
		
		for (int i = 0; i < ListaTelfBen.size(); i++) {
//			System.out.println("ListaTelBen: "+ListaTelfBen.get(i));
			ListaTelefonosBeneficiario+=ListaTelfBen.get(i).getNumero()+" ";
		}
		ListaTelefonosBeneficiario=ListaTelefonosBeneficiario.trim().replaceAll(" ","-");
//		System.out.println("ListaTelefonosBen: "+ListaTelefonosBeneficiario);
		
		
//		System.out.println("idOrdServ: "+idOrdServ);
		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
//		System.out.println("nitSQL: "+nitSQL);
		String nit_patam=(String) nitSQL.get("nitInst");
		System.out.println("nit_param: "+nit_patam);
		String direccionBol=uris.imgJasperReport+"escudobolivia.png";
		String logoGob=uris.imgJasperReport+"escudoGobernacion.png"; 
//		String subReportInst="/app/reportes/getEmpresa.jasper"; 
                            
		String nombreReporte="Incumplimiento Contrato",tipo="pdf", estado="inline";
		System.out.println("logo_param: "+this.getClass().getResourceAsStream(direccionBol));
		    
		Map<String, Object> parametros=new HashMap<String, Object>();
		                       
		String url=uris.jasperReport+"getIContrato.jasper";	        
		parametros.put("idincl_param",Integer.parseInt(idincl));
		parametros.put("telefonosBenf_param",ListaTelefonosBeneficiario);
//		parametros.put("tramitador_param",Tramitador);
		parametros.put("nit_param",nit_patam);
		parametros.put("logo_param",this.getClass().getResourceAsStream(direccionBol));
		parametros.put("logoGob_param",this.getClass().getResourceAsStream(logoGob));

		GeneradorReportes g=new GeneradorReportes();
		try{
			g.generarReporte(res, getClass().getResource(url), tipo, parametros, dataSource.getConnection(), nombreReporte, estado);	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
