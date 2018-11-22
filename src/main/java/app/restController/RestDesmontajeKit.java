package app.restController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorDesmontajeKit;
import app.manager.ManejadorInstalacionKit;
import app.manager.ManejadorServicios;
import app.manager.ManejadorSolicitudes;
import app.models.ActaRecepcion;
import app.models.DesmontajeKit;
import app.models.Persona;
import app.models.RegistroKit;



@RequestMapping("/RestDesmontajeKit/")
@RestController 
public class RestDesmontajeKit{

	@Autowired
	ManejadorInstalacionKit manejadorInstalacionKit;
	@Autowired
	ManejadorServicios manejadorServicios;
	@Autowired 
	ManejadorSolicitudes manejadorSolicitudes;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<DesmontajeKit>> listarServicio(HttpServletRequest req,HttpServletResponse res){	
		List<DesmontajeKit> listaDesmontaje=this.manejadorInstalacionKit.ListarDesmontajes(req);
		System.out.println("L: "+listaDesmontaje);
		for (int i = 0; i < listaDesmontaje.size(); i++){
			listaDesmontaje.get(i).getRegistroKit().setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(listaDesmontaje.get(i).getRegistroKit().getIdordserv()));
			listaDesmontaje.get(i).getRegistroKit().getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(listaDesmontaje.get(i).getRegistroKit().getOrdenServicio().getIdordserv()));			
		}
		System.out.println("listaServicios: "+listaDesmontaje.toString());
		return new ResponseEntity<List<DesmontajeKit>>(listaDesmontaje,HttpStatus.OK);
	}
	@RequestMapping(value="datosModal")
	public ResponseEntity<List<Object>> dataOrdenServicio(){
		List<Object> lista=new ArrayList<Object>();
		Map<String, Object> mapa=new HashMap<>();
		Date date = new Date();
		String fecha= new SimpleDateFormat("yyyy-MM-dd").format(date);
		mapa.put("fecha",fecha);
		List<?> ListaTalleres=this.manejadorServicios.ListTalleres();
		
		lista.add(mapa);
		lista.add(ListaTalleres); 
		System.out.println("Lista: "+lista.toString());
		return new ResponseEntity<List<Object>>(lista,HttpStatus.OK);
	}
	@RequestMapping(value="FiltroRegistroKit")
	public ResponseEntity<List<RegistroKit>> FiltroSolicitud(HttpServletRequest req,HttpServletResponse res){
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String texto=req.getParameter("texto");
		System.out.println("texto: "+texto);
		 
		List<RegistroKit> Lista=null;
		try {
			System.out.println("entro try");
			Lista=this.manejadorInstalacionKit.FiltroInstalacionKit(req.getParameter("texto"));
			System.out.println("Lista: "+Lista.toString());
			for (int i = 0; i < Lista.size(); i++) {
//				Lista.get(i).setRegistroKit(this.manejadorInstalacionKit.getRegistroKitOP(ListaAR.get(i).getIdordserv()));
				Lista.get(i).setCilindros(this.manejadorInstalacionKit.ListaCilindro(Lista.get(i).getIdregistroKit()));
				Lista.get(i).setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(Lista.get(i).getIdordserv()));
				Lista.get(i).getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(Lista.get(i).getIdordserv()));		
			}
			System.out.println("TAM: "+Lista.size());
			System.out.println("Lista: "+Lista);
		} catch (Exception e) {
			System.out.println("entro catch");
			Lista=null;
		}
		return new ResponseEntity<List<RegistroKit>>(Lista,HttpStatus.OK);
	}
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try{
			Object[] consulta=this.manejadorInstalacionKit.registrarDesmontaje(req,xuser);
			System.out.println("resp: "+consulta[0]);
			
			int idregistroKit=Integer.parseInt(req.getParameter("idregistroKit"));
			System.out.println("idregistroKit: "+idregistroKit);
			int getIdsolt=this.manejadorInstalacionKit.getIdSoltByIdRegKit(idregistroKit);
			System.out.println("getIdsolt: "+getIdsolt);
			boolean solicitud=this.manejadorSolicitudes.anularSoltDesmontaje(getIdsolt);
			System.out.println("anulado? "+solicitud);
		
			respuesta.put("estado", consulta[0]);
//			respuesta.put("idRecep",Integer.parseInt(consulta[1].toString()));
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return respuesta;
	}
}
