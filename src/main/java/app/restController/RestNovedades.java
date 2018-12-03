package app.restController;

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

import app.manager.ManejadorSolicitudes;

import app.models.Novedad;
import app.models.Persona;
import app.models.Solicitud;
import app.models.TipoNovedad;

@RequestMapping("/RestNovedades/")
@RestController
public class RestNovedades {
	@Autowired
	ManejadorSolicitudes manejadorSolicitudes;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Novedad>> listarServicio(HttpServletRequest req,HttpServletResponse res){	
		List<Novedad> lista=this.manejadorSolicitudes.ListarNovedades(req);
		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).setPersona(this.manejadorSolicitudes.metPersonaIC(lista.get(i).getLogin()));;
			
		}
		System.out.println("listaNov: "+lista.toString());
		return new ResponseEntity<List<Novedad>>(lista,HttpStatus.OK);
	}
	
	@RequestMapping({"getTipoNovedades"})
	public ResponseEntity<List<TipoNovedad>> getTipoNovedades(HttpServletRequest req){
//		String placa=req.getParameter("placa");
//		System.out.println("la placa es : "+placa);
		List<TipoNovedad> resp=this.manejadorSolicitudes.getTipoNovedades();
		return new ResponseEntity<List<TipoNovedad>>(resp,HttpStatus.OK);
	}
	
	@RequestMapping(value="FiltroNovedad")
	public ResponseEntity<List<Solicitud>> FiltroSolicitud(HttpServletRequest req,HttpServletResponse res){
		String texto=req.getParameter("texto");
		System.out.println("texto: "+texto);
		 
		List<Solicitud> solt=null;
		try {
			solt=this.manejadorSolicitudes.FiltroSolicitudNov(req.getParameter("texto"));
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
		Map<String, Object> respuesta=new HashMap<String, Object>();
		System.out.println("idsolt: "+req.getParameter("idsolt"));
		try {
		
			Object[] Resp=this.manejadorSolicitudes.registrarNov(req,xuser);
			respuesta.put("estado", Resp[0]);
			respuesta.put("idnov", Integer.parseInt(Resp[1].toString()));
			System.out.println("resp:"+respuesta.toString());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println("error cach"+e.getMessage());
			respuesta.put("estado",false);
		}
//		respuesta.put("estado",false);
		return respuesta;
	}
	@RequestMapping(value="habilitarSolicitud")
	public ResponseEntity<Map<String, Object>> habilitarSolicitud(HttpServletRequest req){
		System.out.println("lego eliminar");

		String idsolt=req.getParameter("idsolt");
		String placa=req.getParameter("placa");
		System.out.println("idsolt: "+idsolt);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean resp=this.manejadorSolicitudes.habilitarSolicitud(Integer.parseInt(idsolt),placa);
			respuesta.put("estado", resp);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.OK);	
	}
}
