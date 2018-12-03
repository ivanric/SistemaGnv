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
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorAprobaciones;
import app.manager.ManejadorBeneficiarios;
import app.manager.ManejadorInstalacionKit;
import app.manager.ManejadorServicios;
import app.manager.ManejadorSolicitudes;
import app.models.Accion;
import app.models.Aprobacion;
import app.models.DocumentoBeneficiario;
import app.models.Persona;
import app.models.RegistroKit;
import app.models.Solicitud;
import app.models.Telefono;
import app.models.TransferenciaBeneficiario;
import app.utilidades.GeneradorReportes;
import app.utilidades.URIS;

@RequestMapping("/RestTBeneficiarios/")
@RestController
public class RestTBeneficiarios {
	@Autowired
	ManejadorBeneficiarios manejadorBeneficiarios;
	@Autowired
	ManejadorInstalacionKit manejadorInstalacionKit;
	@Autowired
	ManejadorServicios manejadorServicios;  
	@Autowired 
	ManejadorSolicitudes manejadorSolicitudes;
	
	@Autowired
	ManejadorAprobaciones manejadorAprobaciones;
	 
	@RequestMapping(value="listar")
	public ResponseEntity<List<TransferenciaBeneficiario>> listarTBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<TransferenciaBeneficiario> Tbeneficiarios=this.manejadorBeneficiarios.ListaTB(req);
		System.out.println("Tbeneficiarios: "+Tbeneficiarios);
		for (int i = 0; i < Tbeneficiarios.size(); i++) {
			Tbeneficiarios.get(i).setRegistroKit(this.manejadorInstalacionKit.getRegistroKitTB(Tbeneficiarios.get(i).getIdsolt()));
			Tbeneficiarios.get(i).getRegistroKit().setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(Tbeneficiarios.get(i).getRegistroKit().getIdordserv()));
			Tbeneficiarios.get(i).getRegistroKit().getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(Tbeneficiarios.get(i).getRegistroKit().getIdordserv()));			
		}
		System.out.println("listaTBen: "+Tbeneficiarios.toString());
		return new ResponseEntity<List<TransferenciaBeneficiario>>(Tbeneficiarios,HttpStatus.OK);
	}
	@RequestMapping(value="documentosBeneficiario")
	public ResponseEntity<List<DocumentoBeneficiario>> docuemtosBeneficiario(HttpServletRequest req,HttpServletResponse res){	
		List<DocumentoBeneficiario> listaDocumentos=this.manejadorBeneficiarios.listaDocumentosTB();
		return new ResponseEntity<List<DocumentoBeneficiario>>(listaDocumentos,HttpStatus.OK);
	}
	@RequestMapping(value="FiltroInstalacionKit")
	public ResponseEntity<List<RegistroKit>> FiltroSolicitud(HttpServletRequest req,HttpServletResponse res){
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String texto=req.getParameter("texto");
		System.out.println("texto: "+texto);
		 
		List<RegistroKit> Lista=null;
		try {
			System.out.println("entro try");
			Lista=this.manejadorInstalacionKit.FiltroRegistroKitTB(req.getParameter("texto"));
			System.out.println("Lista: "+Lista.toString());
			for (int i = 0; i < Lista.size(); i++) {
				Lista.get(i).setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(Lista.get(i).getIdordserv()));
				Lista.get(i).getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(Lista.get(i).getIdordserv()));		
				Lista.get(i).setCilindros(this.manejadorInstalacionKit.ListaCilindro(Lista.get(i).getIdregistroKit()));
			}																
			System.out.println("KU: "+Lista);
		} catch (Exception e) {
			System.out.println("entro catch");
			Lista=null;
		}
		return new ResponseEntity<List<RegistroKit>>(Lista,HttpStatus.OK);
	}
	
	
//	@RequestMapping({"getDatosTB"})
//	public ResponseEntity<List<Object>> getDatosSol(HttpServletRequest req){
//		String idsolt=req.getParameter("idsolt");
//		System.out.println("idsoltTB: "+idsolt);
////		List<List<?>> Lista=new ArrayList<List<?>>();
//		List<Object> lista=new ArrayList<Object>();
//		
//		Solicitud solicitud=new Solicitud();
//		solicitud=this.manejadorSolicitudes.ListarSolById(req);
//		List<Aprobacion>listaAprob=this.manejadorSolicitudes.cargarAprobaciones(solicitud);
//		solicitud.setAprobaciones(listaAprob);
//		
//		
//		List<Accion> listaTipAprob=this.manejadorSolicitudes.ListaTipoAprobTB();
//		List<?>	ListPausaAprob=this.manejadorSolicitudes.ListaPausaAprob(req);
//		Map<String, Object> Mapa=new HashMap<String, Object>();
//
//		Mapa.put("ListaAprobaciones",listaTipAprob);
//		Mapa.put("ListPausaAprob", ListPausaAprob);
//		
//		lista.add(Mapa);
//		lista.add(solicitud);
//		return new ResponseEntity<List<Object>>(lista,HttpStatus.OK);
//	}

	@RequestMapping({"getAprobTB"})
	public ResponseEntity<List<Object>> getApTB(HttpServletRequest req){
		String idsolt=req.getParameter("idsolt");
		List<Object> lista=new ArrayList<Object>();

		List<Aprobacion>listaAprob=this.manejadorSolicitudes.cargarAprobacionesTB(Integer.parseInt(idsolt));	
		
		List<Accion> listaTipAprob=this.manejadorSolicitudes.ListaTipoAprobTB();
		List<?>	ListPausaAprobTB=this.manejadorSolicitudes.ListaPausaAprobTB(Integer.parseInt(idsolt));
		Map<String, Object> Mapa=new HashMap<String, Object>();

		Mapa.put("ListaTipoAprobaciones",listaTipAprob);
		Mapa.put("ListPausaAprob", ListPausaAprobTB);
		
		lista.add(Mapa);
		lista.add(listaAprob);
//		lista.add(solicitud);
		return new ResponseEntity<List<Object>>(lista,HttpStatus.OK);
	}
	
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String[] documentos=req.getParameterValues("documentos[]");
		String[] telefonos=req.getParameterValues("telefono[]");
		
		Map<String, Object> respuesta=new HashMap<String, Object>();
		for (String i : documentos) {
			System.out.println("coddocb: "+i);
		}  
		for (String i : telefonos) {
			System.out.println("telefonos: "+i);
		}
		
		
		try {
			
			Object[] consulta=this.manejadorBeneficiarios.registrarTB(req,documentos,telefonos,xuser);
			System.out.println("estado Registrar TB: "+consulta[0]);
			respuesta.put("estado", consulta[0]);
			respuesta.put("idtrasl",Integer.parseInt(consulta[1].toString()));
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		respuesta.put("estado",true);
		return respuesta;
	}
	

	
	@Transactional
	@RequestMapping({"aprobarTB"})
	public ResponseEntity<Map<String, Object>> add(HttpServletRequest req){
		Map<String, Object> resp=new HashMap<String, Object>();
		
		//APROBACION
		String idsolt=req.getParameter("idsolt");
		String idtrasl=req.getParameter("idtrasl");
		System.out.println("idtrasl: "+idtrasl);
		String dataFinal=req.getParameter("dataFinal");
		String idtipoPausa=req.getParameter("idtipoPausa");	
		String descripcionPausa=req.getParameter("descripcionPausa");	
		String[] ListaAprobaciones=req.getParameterValues("aprobacion[]");
		System.out.println("dataFinal:"+dataFinal+" idsolt: "+idsolt+" idTipoPausa: "+idtipoPausa+" descripcionPausa: "+descripcionPausa);

		
		Persona xuser=(Persona)req.getSession().getAttribute("xusuario");

		
		for (String t : ListaAprobaciones) {
			System.out.println("aprob: "+t);
		}
		
		
		int idTipoFinal=this.manejadorAprobaciones.getTipoFinalTB();
		System.out.println("Aprob final: "+idTipoFinal);
		int aprobar=0;

		try {
			
			boolean status;
			if(ListaAprobaciones!=null) {
				if(Integer.parseInt(dataFinal)==idTipoFinal) {
					aprobar=Integer.parseInt(dataFinal);
				}else{
					aprobar=0;
				}
				status=this.manejadorAprobaciones.insertarAprobacionTB(xuser.getUsuario().getLogin(),Integer.parseInt(idtrasl),Integer.parseInt(idsolt),ListaAprobaciones,aprobar);
				System.out.println("Estado de Aprob: "+status);
				if(!descripcionPausa.equals("") && idtipoPausa!=null) {
					this.manejadorAprobaciones.insertarPausaAprobacionTB(Integer.parseInt(idsolt), Integer.parseInt(idtipoPausa), descripcionPausa,xuser.getUsuario().getLogin());
				}
			}else {
				if((!descripcionPausa.equals("")) && (idtipoPausa!=null)) {
					System.out.println("entro sin aprobaciones per si pauso");
					System.out.println("descripcionPausa: "+descripcionPausa);
					int resultPause=this.manejadorAprobaciones.insertarPausaAprobacionTB(Integer.parseInt(idsolt), Integer.parseInt(idtipoPausa), descripcionPausa,xuser.getUsuario().getLogin());
					System.out.println("Resultado Pausa: "+resultPause);
				}
			}
			
			
			
			resp.put("status", true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resp.put("status", false);
		}

		return new ResponseEntity<Map<String,Object>>(resp,HttpStatus.OK);
	}
	
	@RequestMapping(value="Ver")
	public ResponseEntity<List<?>> VerSolicitud(HttpServletRequest req,HttpServletResponse res){
//		Persona us=(Persona)req.getSession(true).getAttribute("xusuario");
		List<Object> lista=new ArrayList<>();
		String idtrasl=req.getParameter("idtrasl");
		System.out.println("idtrasl: "+idtrasl);
		TransferenciaBeneficiario tb=this.manejadorBeneficiarios.verTBeneficiario(Integer.parseInt(idtrasl));
		RegistroKit rk=this.manejadorInstalacionKit.getRegistroKitTBbyIdTrasl(Integer.parseInt(idtrasl));
		rk.setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(rk.getIdordserv()));
		rk.getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(rk.getOrdenServicio().getIdordserv())); 
		rk.setCilindros(this.manejadorInstalacionKit.ListaCilindro(rk.getIdregistroKit()));
		tb.setRegistroKit(rk);
		lista.add(tb);
		lista.add(this.manejadorSolicitudes.listaDocumentos());
		return new ResponseEntity<List<?>>(lista,HttpStatus.OK);
	}
	@Autowired
	DataSource dataSource;
	@RequestMapping("Imprimir")
	public  void Imprimir(HttpServletResponse res,HttpServletRequest req){
		URIS uris=new URIS();
		Persona us=(Persona)req.getSession(true).getAttribute("xusuario");
		String Tramitador=us.getAp().toUpperCase()+" "+us.getAm().toUpperCase()+" "+us.getNombres().toUpperCase();    
		String id=req.getParameter("idtrasl");
//		System.out.println("idTrasl: "+id);
		
		TransferenciaBeneficiario tb=this.manejadorBeneficiarios.verTBeneficiario(Integer.parseInt(id));
		RegistroKit rk=this.manejadorInstalacionKit.getRegistroKitTBbyIdTrasl(Integer.parseInt(id));
		rk.setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(rk.getIdordserv()));
		rk.getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(rk.getOrdenServicio().getIdordserv())); 
		tb.setRegistroKit(rk);
		
		//System.out.println("ID1: "+tb.getPersonaAnteriorBenf().getIdper());
		//System.out.println("ID2: "+tb.getRegistroKit().getOrdenServicio().getSolicitud().getPersona().getIdper());
		
		String ListaTelefonosAB="",ListaTelefonosNB="";
		
		List<Telefono> ListaTelfAB=this.manejadorBeneficiarios.ListaTelf_TB(tb.getPersonaAnteriorBenf().getIdper());
//		System.out.println("ListaTelfAB: "+ListaTelfAB.toString());
		for (int i = 0; i < ListaTelfAB.size(); i++) {
//			System.out.println("ListaTelfAB: "+ListaTelfAB.get(i));
			ListaTelefonosAB+=ListaTelfAB.get(i).getNumero()+" ";
		}
		ListaTelefonosAB=ListaTelefonosAB.trim().replaceAll(" ","-");
//		System.out.println("ListaTelefonosAB: "+ListaTelefonosAB);
		 
		
		
		List<Telefono> ListaTelfNB=this.manejadorBeneficiarios.ListaTelf_TB(tb.getPersonaNuevoBenf().getIdper());
//		System.out.println("ListaTelfNB: "+ListaTelfNB.toString());
		for (int i = 0; i < ListaTelfNB.size(); i++) {
//			System.out.println("ListaTelfNB: "+ListaTelfNB.get(i));
			ListaTelefonosNB+=ListaTelfNB.get(i).getNumero()+" ";
		}
		ListaTelefonosNB=ListaTelefonosNB.trim().replaceAll(" ","-");
//		System.out.println("ListaTelefonosNB: "+ListaTelefonosNB);
	 	 
	
		  
		String escudo=uris.imgJasperReport+"escudobolivia.png";        
		String nombreReporte="TRANSFERENCIA BENEFICIARIO",tipo="pdf", estado="inline";
//		System.out.println("escudo: "+this.getClass().getResourceAsStream(escudo));
		      
		Map<String, Object> parametros=new HashMap<String, Object>();         
		String url=uris.jasperReport+"getTBeneficiario.jasper"; 	
	                                
		parametros.put("idTrasl_param",Integer.parseInt(id));
		parametros.put("telefonosAB_param",ListaTelefonosAB);
		parametros.put("telefonosNB_param",ListaTelefonosNB);
		parametros.put("tramitador_param",Tramitador);
		parametros.put("escudo_param",this.getClass().getResourceAsStream(escudo));
		GeneradorReportes g=new GeneradorReportes();
		try{
			g.generarReporte(res, getClass().getResource(url), tipo, parametros, dataSource.getConnection(), nombreReporte, estado);	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
 